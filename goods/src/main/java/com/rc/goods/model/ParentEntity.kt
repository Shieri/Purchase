package com.rc.goods.model

/**
 * Created by dingchao on 2018/1/4.
 */

class ParentEntity {
    var id: Int = 0//id
    var area_name: String? = null//影院名称
    var cinemas: List<ChildEntity>? = null//子影院

    constructor() {}

    constructor(id: Int, area_name: String, cinemas: List<ChildEntity>) {
        this.id = id
        this.area_name = area_name
        this.cinemas = cinemas
    }
}
