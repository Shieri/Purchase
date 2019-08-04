package com.rc.base.injection.module

import android.app.Activity
import com.rc.base.injection.ActivityScope
import dagger.Module
import dagger.Provides


/*
    Activity级别Module
 */
@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun provideActivity(): Activity {
        return this.activity
    }
}
