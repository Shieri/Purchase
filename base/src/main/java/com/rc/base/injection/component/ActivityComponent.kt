package com.rc.base.injection.component

import android.app.Activity
import android.content.Context
import com.rc.base.injection.ActivityScope
import com.rc.base.injection.module.ActivityModule
import dagger.Component

/*
    Activity级别Component
 */
@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class),modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun activity():Activity
    fun context(): Context

}
