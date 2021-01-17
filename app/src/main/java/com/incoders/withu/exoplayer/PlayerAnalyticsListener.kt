package com.incoders.withu.exoplayer

import com.incoders.withu.exoplayer.PlayerStatic.currentItemIndex
import com.incoders.withu.exoplayer.PlayerStatic.currentPlayingPos
import com.incoders.withu.exoplayer.PlayerStatic.isPlaying
import com.incoders.withu.exoplayer.PlayerStatic.playbackState
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.analytics.AnalyticsListener

//Analytics Listener for the player
//For listen to event and post value to the live data objects defined in playerStatic Object

object PlayerAnalyticsListener {
    val analyticsListener = object : AnalyticsListener{

        // When there is any error We can see in log
        override fun onPlayerError(
            eventTime: AnalyticsListener.EventTime,
            error: ExoPlaybackException
        ) {
            super.onPlayerError(eventTime, error)
            error.printStackTrace()
        }

        //When playing change like play pause..
        override fun onIsPlayingChanged(eventTime: AnalyticsListener.EventTime, isPlay: Boolean) {
            isPlaying.postValue(isPlay)
        }

        // When state change like Error, Buffering, Ended
        override fun onPlaybackStateChanged(eventTime: AnalyticsListener.EventTime, state: Int) {
            playbackState.postValue(state)
        }

        // When any new audio is played
        override fun onMediaItemTransition(
            eventTime: AnalyticsListener.EventTime,
            mediaItem: MediaItem?,
            reason: Int
        ) {
            super.onMediaItemTransition(eventTime, mediaItem, reason)
            currentItemIndex.postValue(eventTime.currentWindowIndex)
        }

        override fun onSeekStarted(eventTime: AnalyticsListener.EventTime) {
            super.onSeekStarted(eventTime)
            currentPlayingPos.postValue(eventTime.currentPlaybackPositionMs)
        }
    }
}