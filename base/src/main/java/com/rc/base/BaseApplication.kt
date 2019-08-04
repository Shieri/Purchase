package com.rc.base

import android.app.Application
import android.content.Context
import com.rc.base.injection.component.AppComponent
import com.rc.base.injection.component.DaggerAppComponent
import com.rc.base.injection.module.AppModule

open class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initAppInjection()

        context = this

    }

    /*
    Application Component初始化
 */
    private fun initAppInjection() {

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        //appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    /*
    全局伴生对象
 */
    companion object {
        lateinit var context: Context
    }
}