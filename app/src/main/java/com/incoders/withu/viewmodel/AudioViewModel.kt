package com.incoders.withu.viewmodel


import android.app.Application
import android.media.MediaMetadataRetriever
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log.d
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.incoders.withu.api.FileUploadService
import com.incoders.withu.api.RetrofitClient
import com.incoders.withu.exoplayer.PlayerAnalyticsListener.analyticsListener
import com.incoders.withu.exoplayer.PlayerStatic.currentPlayingPos
import com.incoders.withu.util.AUDIOFILEPATH
import com.incoders.withu.util.AudioApplication
import com.incoders.withu.util.Item
import com.incoders.withu.util.Methods.longToTime
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AudioViewModel(application: Application): AndroidViewModel(application) {
    private var recorder : MediaRecorder? = null
    private val context = getApplication<AudioApplication>()
    val btRecorderText = MutableLiveData<String>()
    val startnavigation = MutableLiveData<String>()
    val pauseButtonText = MutableLiveData<String>()
    val pauseButtonState = MutableLiveData<Boolean>()
    val startList = MutableLiveData<MutableList<Item>>()
    val currTime = MutableLiveData<String>()
    val newItem = MutableLiveData<Item>()

    private val list = mutableListOf<Item>()
    private var tempFileName = ""
    private var tempTime = 0L
    private var isRecording = false
    private var isRecordingPaused = false

     val path = "${context.externalCacheDir?.absolutePath}"
     val fileName2 = "gg"
    val player = SimpleExoPlayer.Builder(context).build()
    private val factory = ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context,Util.getUserAgent(context,"Audio Recorder")))


    init {
        player.addAnalyticsListener(analyticsListener)
        listFiles()
    }

  
    fun record(){
        if(!isRecording && !isRecordingPaused) {
            startRecording()

        }
        else stopRecording()
    }


    fun uploadFIleToServer(
        encodedAudioFile: MultipartBody.Part,
        descriptionn: RequestBody
    ) {


        val params: MutableMap<String, MultipartBody.Part> = HashMap()


        val request = RetrofitClient.buildService(FileUploadService::class.java)
        val call = request.uploadAudio(descriptionn,encodedAudioFile)
        val call2 = request.getHelloWolrd()
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful()) {
                    if (response.body() == "False"){
                        startnavigation.postValue("False")
                    }
                    d("SPD","Server has responded File uploaded succesfully :) ${response.body()}")
                } else {
                    d("SPD","respnse not success --- error  ${response.raw().toString()}")
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                d("SPD","Server has responded File not uploaded :(")

            }
        })
    }

    // Start the recording
     fun startRecording(){
        tempFileName = "${System.currentTimeMillis()}.mp3"
        val fileName = "${context.externalCacheDir?.absolutePath}/$tempFileName"
        AUDIOFILEPATH = fileName
        recorder = MediaRecorder().apply {          //  Creating the Instance of Media Recorder
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(fileName)
            try {
                prepare()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
            start()
        }
            isRecording = true
            startRecorderTimer()
            visiblePauseButton()
            btRecorderText.postValue("STOP") // Change the text of recorder button on Starting the recording
    }

    // After release the recorder Stop the recording and add the recording to the recycler view in recorder fragment
    fun stopRecording(){
        closeRecording()
        invisiblePauseButton()
        btRecorderText.postValue("RECORD")  // Change the text of recorder button after completing the recording
        val item = Item(tempFileName,tempTime)
        newItem.postValue(item)
        isRecording = false
        isRecordingPaused = false
        tempTime = 0L
        currTime.postValue(longToTime(tempTime))
    }

    // Release the media recorder
    fun closeRecording(){
        recorder?.stop()
        recorder?.release()
        recorder = null
    }

    // Start the player timer or get value of player and update the seekBar
    fun startPlayerTimer(){
        var runnable:Runnable? = null
        val handler = Handler()
        runnable = Runnable {
            if(player.playbackState == ExoPlayer.STATE_READY || player.playbackState== ExoPlayer.STATE_BUFFERING){
                currentPlayingPos.postValue(player.currentPosition)
                handler.postDelayed(runnable!!, 1000)
            }
        }
        handler.postDelayed(runnable, 0)
    }

    // Start the upper time in recording fragment using runnable
    private fun startRecorderTimer(){
        var runnable: Runnable? = null
        val handler = Handler()
        runnable = Runnable {
            if(isRecording){
                tempTime += 1000
                handler.postDelayed(runnable!!, 1000)
            }
            currTime.postValue(longToTime(tempTime))
        }
        handler.postDelayed(runnable, 0)
    }

    // get all the previous recordings saved in memory and add in list
    private fun listFiles(){
        val path = path
        val directory = File(path)
        val files: Array<File> = directory.listFiles()!!

        val mmr = MediaMetadataRetriever()      // Metadata retriever for getting the duration

        for (i in files) {
            val uri: Uri = Uri.parse("$path/${i.name}")
            mmr.setDataSource(context, uri)
            val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            val millis = durationStr.toLong()
            list.add(Item(i.name,millis))
        }
        startList.postValue(list)
    }

    // Play audio in using exoPlayer by taking the name and adding it to path
    fun playAudio(name: String){
        player.clearMediaItems()
        player.setMediaSource(factory.createMediaSource(MediaItem.fromUri("$path/$name")))
        player.prepare()
        player.playWhenReady = true
    }

    //Release the player when app goes to the background
    fun releasePlayer(){
        player.release()
    }

    // Making visible the pause Button on starting the recording
    private fun visiblePauseButton(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) pauseButtonState.postValue(true)
    }

    //Making invisible the pause Button on Completing the recording
    private fun invisiblePauseButton(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) pauseButtonState.postValue(false)
    }

    //Pause/Resume recorder on api>24
    fun pauseOrResumeRecorder(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            if(isRecording) {
                recorder?.pause()
                pauseButtonText.postValue("RESUME")
                isRecordingPaused = true
            }
            else {
                recorder?.resume()
                pauseButtonText.postValue("PAUSE")
                isRecordingPaused = false
            }
            isRecording = !isRecording
            startRecorderTimer()
        }
    }
}