package com.rc.goods.model

data class Cart(
    val group: List<Group>,
    val child: List<Child>
)

data class Group(
    val name: String
)

data class Child(
    val id: Int,
    val goodsCount: Int,
    val goodsDesc: String,
    val goodsIcon: String,
    val goodsId: Int,
    val goodsPrice: Double,
    val goodsSku: String,
    val isSelected: Boolean
)