package com.rc.goods.contract

import com.base.mvp.BasePresenter
import com.base.mvp.BaseView
import com.rc.goods.model.Cart

interface CartContract {

    interface View: BaseView {

        fun test(dates:List<Cart>)


    }

    interface Presenter: BasePresenter<View> {

    }
}