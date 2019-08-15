package com.rc.goods.ui.activity

import android.os.Bundle
import com.rc.base.mvp.BaseActivity
import com.rc.base.utils.ActivityUtils
import com.rc.goods.R
import com.rc.goods.injection.component.DaggerGoodsComponent
import com.rc.goods.ui.fragment.CartFragment
import javax.inject.Inject
import android.app.Activity
import android.view.View
import android.view.View.*
import android.view.Window
import android.view.WindowManager
import android.widget.Toast


class CartActivity : BaseActivity(){


    @Inject
    lateinit var cartFragment: CartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) //隐藏状态栏
        setContentView(R.layout.cart_act)



        var cartFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as CartFragment?

        if (cartFragment == null) {
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager,
                this.cartFragment, R.id.contentFrame
            )
        }

        getStateBar3()

       // setNavigationBar(this,GONE)
    }


    override fun injectComponent() {

         DaggerGoodsComponent.builder().activityComponent(mActivityComponent).build().inject(this)
    }

    private fun getStateBar3(){
        var result = 0;
        var resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show()
    }


    fun setNavigationBar(activity: Activity, visible: Int) {
        val decorView = activity.window.decorView
        //显示NavigationBar
        if (View.GONE == visible) {
            val option = SYSTEM_UI_FLAG_HIDE_NAVIGATION
            decorView.systemUiVisibility = option
        }
    }


}