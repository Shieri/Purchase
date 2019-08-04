package com.rc.goods.model

data class Cart(
    val links: List<Link>
)

data class Link(
    val name: String,
    val url: String
)