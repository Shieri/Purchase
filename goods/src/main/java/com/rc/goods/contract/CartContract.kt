package com.rc.goods.contract

import com.base.mvp.BasePresenter
import com.base.mvp.BaseView

interface CartContract {

    interface View: BaseView {

        fun test()


    }

    interface Presenter: BasePresenter<View> {

    }
}