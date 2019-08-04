package com.rc.app.injection

import com.rc.app.MainActivity
import com.rc.base.injection.ActivityScope
import com.rc.base.injection.PerComponentScope
import com.rc.base.injection.component.ActivityComponent
import com.rc.base.injection.component.AppComponent
import com.rc.base.injection.module.ActivityModule
import dagger.Component


@PerComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class))
interface PayComponent  {

    fun inject(activity:MainActivity)

}