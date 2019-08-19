package com.rc.goods.model

data class Cart(
    val child: List<Child>,
    val id: Int,
    var isChecked: Boolean,
    val parentName: String
)

data class Child(
    var count: Int,
    val id: String,
    val image: String,
    var isChecked: Boolean,
    val name: String,
    val price: Double
)