package com.ninhtbm.vcl

import android.app.Application
import com.ninhtbm.vcl.di.businessModules
import com.ninhtbm.vcl.di.coreModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class VCLApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(coreModules, businessModules)
        }
    }
}