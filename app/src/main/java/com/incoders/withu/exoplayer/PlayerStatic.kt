package com.incoders.withu.exoplayer

import androidx.lifecycle.MutableLiveData

// Live data objects for listening to the player and change in view model
object PlayerStatic {

    // For current audio position, Used in seekbar
    val currentPlayingPos = MutableLiveData<Long>()

    // When any new audio is played
    val currentItemIndex = MutableLiveData<Int>()

    // for checking playing state like playing or not
    val isPlaying = MutableLiveData<Boolean>()

    //for checking player state, Our only use for StateEnded
    val playbackState = MutableLiveData<Int>()
}