package com.rc.goods.model

data class Cart(
    val child: List<Child>,
    val name: String
)

data class Child(
    val goodsCount: Int,
    val goodsDesc: String,
    val goodsIcon: String,
    val goodsId: Int,
    val goodsPrice: Double,
    val goodsSku: String,
    val id: Int,
    val isSelected: Boolean
)