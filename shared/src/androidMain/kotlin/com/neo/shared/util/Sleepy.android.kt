package com.neo.shared.util

actual fun threadSleep(millis: Long) {
    Thread.sleep(millis)
}