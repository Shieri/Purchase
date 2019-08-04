package com.rc.goods.injection.component

import com.rc.base.injection.PerComponentScope
import com.rc.base.injection.component.ActivityComponent
import com.rc.goods.ui.activity.CartActivity
import dagger.Component

/*
    订单Component
 */
@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class))
interface GoodsComponent {


    fun inject(activity:CartActivity)


}
