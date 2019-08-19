package com.rc.base.ext

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.rc.base.utils.GlideUtils

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method:() -> Unit): View {
    setOnClickListener { method() }
    return this

}

/*
    ImageView加载网络图片
 */
fun AppCompatImageView.loadUrl(url: String) {
     GlideUtils.loadUrlImage(context, url, this)
}