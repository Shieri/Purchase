package com.rc.goods.presenter

import android.util.Log
import com.base.mvp.BaseView
import com.library.RetrofitFactory
import com.rc.goods.api.CartApi
import com.rc.goods.contract.CartContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class CartPresenter @Inject constructor() : CartContract.Presenter {

    private lateinit var mView:CartContract.View


    override fun takeView(view: CartContract.View) {
        mView = view
    }


    override fun start() {
        RetrofitFactory.getInstance("https://raw.githubusercontent.com/")
            .create(CartApi::class.java)
            .getCartList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                mView.test(it)

            })

    }




}