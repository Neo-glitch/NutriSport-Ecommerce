package com.neo.shared.util

import platform.posix.usleep

actual fun threadSleep(millis: Long) {
    usleep((millis * 1000).toUInt())
}