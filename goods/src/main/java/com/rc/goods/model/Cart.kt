package com.rc.goods.model

data class Cart(

    val area_name: String,
    val cinemas: List<Cinema>,
    val id: Int,
    val isChecked: Boolean
)

data class Cinema(
    val area_id: String,
    val area_name: String,
    val cinema_address: String,
    val cinema_name: String,
    val id: String,
    val logo: String
)