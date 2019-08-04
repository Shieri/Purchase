package com.rc.base.mvp

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.jaeger.library.StatusBarUtil
import com.rc.base.BaseApplication
import com.rc.base.R
import com.rc.base.injection.component.ActivityComponent
import com.rc.base.injection.component.DaggerActivityComponent
import com.rc.base.injection.module.ActivityModule

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mActivityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // setContentView(setLayoutId())
      //  initActivityInjection()

        initActivityInjection()
        injectComponent()

        //避免切换横竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        //设置沉浸式
        setStatusBar()
       // initView()


        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("请检查网络是否连接")
        }

    }



    override fun onDestroy() {
        super.onDestroy()
    }


   // abstract fun setLayoutId(): Int


    //abstract fun initView()


    protected open fun setStatusBar() {
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary))
    }


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