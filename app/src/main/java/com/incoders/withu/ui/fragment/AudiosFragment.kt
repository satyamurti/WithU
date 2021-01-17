package com.incoders.withu.ui.fragment


import com.incoders.withu.ui.MainActivity
import com.incoders.withu.util.Item
import com.incoders.withu.util.RvAudioAdapter
import com.incoders.withu.viewmodel.AudioViewModel
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.incoders.withu.R
import com.incoders.withu.exoplayer.PlayerStatic.currentItemIndex
import com.incoders.withu.exoplayer.PlayerStatic.currentPlayingPos
import com.incoders.withu.exoplayer.PlayerStatic.isPlaying
import com.incoders.withu.exoplayer.PlayerStatic.playbackState
import kotlinx.android.synthetic.main.fragment_audios.*
import kotlin.random.Random


class AudiosFragment : Fragment(R.layout.fragment_audios) {

    lateinit var rvAudioAdapter: RvAudioAdapter     //Recycler view adapter for showing audio list in audios fragment
    private lateinit var viewModel: AudioViewModel  //View Model
    lateinit var audioList: MutableList<Item>       //List of all the audios showing in audios fragment
    lateinit var player: SimpleExoPlayer
    private var isShuffleMode = false               // boolean for checking shuffle mode
    private var currentPlayingIndex: Int = -1       // for tracking the current index in audioList of currently playing audio

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).audioViewModel //View model defined in Main Activity
        audioList = mutableListOf()       //List of all the audios showing in audios fragment

        setUpRecyclerView()

        player = viewModel.player

        //In Start take the list of all previous recording
        viewModel.startList.observe(activity as MainActivity, Observer {
            audioList.addAll(it)
            rvAudioAdapter.notifyDataSetChanged()
        })

        //On every complete recording add a new item to the list and also in recycler view
        viewModel.newItem.observe(activity as MainActivity, Observer {
            audioList.add(it)
            rvAudioAdapter.notifyItemInserted(audioList.size-1)
        })

        //On changing to new audio in player change the title and max value for the seekbar
        currentItemIndex.observe(activity as MainActivity, Observer {
            tvTitle.text = audioList[currentPlayingIndex].name
            seekBar.max = audioList[currentPlayingIndex].duration.toInt()
            viewModel.startPlayerTimer()  // function for update seekbar regularly
        })

        // playing position to update the seekbar
        currentPlayingPos.observe(activity as MainActivity, Observer {
            seekBar.progress = it.toInt()
        })

        // Observing the current playing state and changing the icon
        isPlaying.observe(activity as MainActivity, Observer {
            if(it) changeToPauseIcon()
            else changeToPlayIcon()
        })

        //On STATE_ENDED invisible the palyer and currentPlaying index to it's starting position
        playbackState.observe(activity as MainActivity, Observer {
            if(it == ExoPlayer.STATE_ENDED) {
                playerLayout.visibility = View.GONE
                currentPlayingIndex = -1
            }
        })

        //Tracking the seekBar changes, Mainly when the user tap or change the seekbar position
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = Unit

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            //Taking the last seekbar position
            override fun onStopTrackingTouch(seekBar: SeekBar?){
                if(seekBar != null) player.seekTo(seekBar.progress.toLong())
            }
        })

        //On Clicking any item in recycler view playing the the clicked audio
        rvAudioAdapter.setOnClickListener {
            viewModel.playAudio(audioList[it].name)
            currentPlayingIndex = it
            playerLayout.visibility = View.VISIBLE
        }

        //When the user click on play/pause button, according the player condition playing or pausing
        ivPlayPause.setOnClickListener {
            if(player.isPlaying) pause()
            else play()
        }

        // On Clickign next button
        ivNext.setOnClickListener {
            if (currentPlayingIndex != -1){   // Safety check
                if(isShuffleMode) setShufflePlayingIndex()  // if shuffle is on go for shuffle index
                else{
                    // check if next audio available then play previous otherwise show toast message
                    if(audioList.size-1 > currentPlayingIndex) viewModel.playAudio(audioList[++currentPlayingIndex].name)
                    else Toast.makeText(context,"Not Available",Toast.LENGTH_SHORT).show()
                }
            }
        }

        ivPrev.setOnClickListener {
            if (currentPlayingIndex != -1){    //Safety check
                if (isShuffleMode) setShufflePlayingIndex() // if shuffle is on go for shuffle index
                else{
                    // check if previous audio available then play previous otherwise show toast message
                    if(0 < currentPlayingIndex) viewModel.playAudio(audioList[--currentPlayingIndex].name)
                    else Toast.makeText(context,"Not Available",Toast.LENGTH_SHORT).show()
                }
            }
        }

        //On Clicking the shuffle button change isShuffleMode value
        ivShuffle.setOnClickListener {
            if (isShuffleMode) offShuffle()
            else onShuffle()
            isShuffleMode = !isShuffleMode
        }

        //On Clicking the repeat button off repeat or change to RepeatOne Mode so it can play the current song again and again
        ivRepeat.setOnClickListener {
            if(player.repeatMode != ExoPlayer.REPEAT_MODE_OFF) offRepeat()
            else onRepeat()
        }
    }

    // SettingUp the recycler view
    private fun setUpRecyclerView(){
        rvAudioAdapter = RvAudioAdapter(audioList)
        rvAudio.apply {
            adapter = rvAudioAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    // Play the exoPlayer
    private fun play(){
        player.play()
        changeToPauseIcon()
    }

    // Change the play/Pause button Icon to Pause Icon
    private fun changeToPauseIcon(){
        ivPlayPause.setImageDrawable(
                context?.let { c ->
                    ContextCompat.getDrawable(c, R.drawable.ic_baseline_pause_24)
                }
        )
    }

    // Pause the exoPlayer
    private fun pause(){
        player.pause()
        changeToPlayIcon()
    }

    // Change the Play paly/pause button icon to play icon
    private fun changeToPlayIcon(){
        ivPlayPause.setImageDrawable(
                context?.let { c ->
                    ContextCompat.getDrawable(c, R.drawable.ic_baseline_play_arrow_24)
                }
        )
    }

    //Change the Shuffle Button background
    private fun onShuffle(){
        isShuffleMode = true
        ivShuffle.background = context?.let { c ->
            ContextCompat.getDrawable(c, R.drawable.item_enable) }
    }

    // Null the shuffle button background
    private fun offShuffle(){
        isShuffleMode = false
        ivShuffle.background = null
    }

    // Change the repeat button background
    private fun onRepeat(){
        player.repeatMode = Player.REPEAT_MODE_ONE
        ivRepeat.background = context?.let { c ->
            ContextCompat.getDrawable(c, R.drawable.item_enable) }
    }

    // Null the repeat button background
    private fun offRepeat(){
        player.repeatMode = Player.REPEAT_MODE_OFF
        ivRepeat.background = null
    }

    //Generate a random index between the available range
    private fun setShufflePlayingIndex(){
        currentPlayingIndex = Random.nextInt(audioList.size)
        viewModel.playAudio(audioList[currentPlayingIndex].name)
    }

    //When app goes background release Player
    override fun onStop() {
        super.onStop()
        viewModel.releasePlayer()
    }
}