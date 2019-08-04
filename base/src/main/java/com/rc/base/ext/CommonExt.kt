package com.rc.base.ext

import android.view.View



/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method:() -> Unit): View {
    setOnClickListener { method() }
    return this

}