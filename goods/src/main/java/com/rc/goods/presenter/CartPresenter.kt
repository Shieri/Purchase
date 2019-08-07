package com.rc.goods.presenter

import android.util.Log
import com.base.mvp.BaseView
import com.library.RetrofitFactory
import com.rc.goods.contract.CartContract
import javax.inject.Inject

class CartPresenter @Inject constructor() : CartContract.Presenter {

    private lateinit var mView:CartContract.View


    override fun takeView(view: CartContract.View) {
        mView = view
    }


    override fun start() {
        RetrofitFactory.getInstance("")
    }




}