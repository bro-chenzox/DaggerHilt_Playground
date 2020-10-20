package com.palchak.sergey.daggerhiltplayground

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp // Special annotation for extended class of Application class
class MyApplication : Application() {
}