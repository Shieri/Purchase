package com.rc.goods.ui.activity

import android.os.Bundle
import com.rc.base.mvp.BaseActivity
import com.rc.base.utils.ActivityUtils
import com.rc.goods.R
import com.rc.goods.injection.component.DaggerGoodsComponent
import com.rc.goods.ui.fragment.CartFragment
import javax.inject.Inject

class CartActivity : BaseActivity(){


    @Inject
    lateinit var cartFragment: CartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_act)

        var cartFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as CartFragment?

        if (cartFragment == null) {
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager,
                this.cartFragment, R.id.contentFrame
            )
        }

    }


    override fun injectComponent() {

         DaggerGoodsComponent.builder().activityComponent(mActivityComponent).build().inject(this)
    }





}