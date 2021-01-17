package com.incoders.withu.ui.fragment

import com.incoders.withu.ui.MainActivity
import com.incoders.withu.viewmodel.AudioViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.incoders.withu.R
import com.incoders.withu.util.AUDIOFILEPATH
import kotlinx.android.synthetic.main.fragment_recorder.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.lang.reflect.Type

class RecorderFragment : Fragment(R.layout.fragment_recorder) {
    private lateinit var viewModel: AudioViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).audioViewModel       //Taking view model from Main Activity

        //Track the position of the media recorder and change the text of the record button
        viewModel.btRecorderText.observe(activity as MainActivity, Observer {
            btRecord.text = it
        })

        //Track the recording time
        viewModel.currTime.observe(activity as MainActivity, Observer {
            tvTime.text = it
        })

        //Setting the visibility of pause Button
        viewModel.pauseButtonState.observe(activity as MainActivity, Observer {
            if(it) btPause.visibility = View.VISIBLE
            else btPause.visibility = View.GONE
        })

        //Changing the text of pause button after clicking it
        viewModel.pauseButtonText.observe(activity as MainActivity, Observer {
            btPause.text = it
        })
        viewModel.startnavigation.observe(activity as MainActivity, Observer {
            if(it == "True") {

            }
            else {
                d("SPD","User is not in danger")
            }
        })
        // After checking the permission start recording or stop recording
        btRecord.setOnClickListener {
            if (checkPermission()){
                viewModel.record()
                Handler().postDelayed({
                    Log.d("SPD", "Inside Handler of fragment")
                    uploadFIleToServerr()
                }, 5000)

            }
        }

        btPause.setOnClickListener {
            viewModel.pauseOrResumeRecorder()
        }
    }
    fun uploadFIleToServerr(){
        viewModel.stopRecording()
        Log.d("SPD", "will convert file to string ")

        val encodedAudioFile : String
        try {
            val myUri = Uri.fromFile(File(AUDIOFILEPATH))

            val file: File = File(myUri.path)
            d("SPD","${myUri.path.toString()}")

            val requestFile = RequestBody.create(
                MediaType.parse(getMimeType(AUDIOFILEPATH)),
                file
            )

            val body = MultipartBody.Part.createFormData("myfile", file.name, requestFile)


            val sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(activity)
            val gson = Gson()
            val json = sharedPrefs.getString("keywords", "")
            val type: Type =
                object : TypeToken<List<String?>?>() {}.type
            val arrayList: List<String> =
                gson.fromJson<List<String>>(json, type)
            val joinedarrray = arrayList.joinToString(separator = ",")
            val description = RequestBody.create(
                MultipartBody.FORM, joinedarrray)


//            val inputStream: InputStream = this@MainActivity.getContentResolver().openInputStream(myUri)!!
//            val audioInBytes: ByteArray
//            audioInBytes = ByteArray(inputStream.available())
//            inputStream.read(audioInBytes)
//            encodedAudioFile = Base64.encodeToString(audioInBytes, Base64.DEFAULT)
            viewModel.uploadFIleToServer(body,description)

        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("SPD", "failed to convert file to string $e")

        }


    }
    fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
    // Check and, if not given the ask for the Record_Audio permission
    private fun checkPermission(): Boolean{
        return if(ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) true
        else {
            ActivityCompat.requestPermissions(activity as MainActivity,arrayOf(Manifest.permission.RECORD_AUDIO),2)
            false
        }
    }

    //When app goes to background close close or release and null the recorder
    override fun onStop() {
        super.onStop()
        viewModel.closeRecording()
    }
}