package com.rc.goods.api

import com.rc.goods.model.Cart
import io.reactivex.Observable
import retrofit2.http.GET

/*
    购物车 接口
 */
interface CartApi {
    /*
        获取购物车列表
     */
    @GET("Shieri/Purchase/master/goods/src/main/java/com/rc/goods/model/expan.json")
        fun getCartList(): Observable<List<Cart>>


}
