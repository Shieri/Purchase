package com.rc.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rc.base.BaseApplication
import com.rc.base.injection.component.ActivityComponent
import com.rc.base.injection.component.DaggerActivityComponent
import com.rc.base.injection.module.ActivityModule

abstract class BaseActivity : AppCompatActivity()  {

    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }

    /*
    初始Activity Component
 */
    private fun initActivityInjection() {

        mActivityComponent =  DaggerActivityComponent.builder()
                              .appComponent((application as BaseApplication).appComponent)
                              .activityModule(ActivityModule(this)).build()

    }


    /*
        Dagger注册
     */
    protected abstract fun injectComponent()

}