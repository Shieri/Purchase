package com.rc.goods.model

/**
 * Created by dingchao on 2018/1/4.
 */

class ChildEntity {
    /**
     * id : 1
     * cinema_name : DMC昌平保利影剧院
     * cinema_address : 昌平区鼓楼南街佳莲时代广场4楼
     * area_id : 117
     * area_name : 昌平区
     * logo : http://img.komovie.cn/cinema/14205106672446.jpg
     */

    var id: String? = null
    var cinema_name: String? = null
    var cinema_address: String? = null
    var area_id: Int = 0
    var area_name: String? = null
    var logo: String? = null

    constructor() {}

    constructor(
        id: String,
        cinema_name: String,
        cinema_address: String,
        area_id: Int,
        area_name: String,
        logo: String
    ) {
        this.id = id
        this.cinema_name = cinema_name
        this.cinema_address = cinema_address
        this.area_id = area_id
        this.area_name = area_name
        this.logo = logo
    }

}
