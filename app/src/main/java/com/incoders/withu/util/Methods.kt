package com.incoders.withu.util

import java.util.concurrent.TimeUnit

object Methods {
    // Utility method for Change long milliseconds to formatted string
    fun longToTime(millis: Long): String{
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
            TimeUnit.MILLISECONDS.toSeconds(millis) % 60);
    }
}