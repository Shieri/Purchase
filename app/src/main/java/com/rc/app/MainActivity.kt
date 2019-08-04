package com.rc.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rc.app.injection.DaggerPayComponent
import com.rc.base.utils.ActivityUtils
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var cartFragment: CartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cartFragment = supportFragmentManager.findFragmentById(R.id.contentFrame) as CartFragment?

        if (cartFragment == null) {
            ActivityUtils.addFragmentToActivity(
                supportFragmentManager,
                this.cartFragment, R.id.contentFrame
            )
        }

    }


    override fun injectComponent() {

        DaggerPayComponent.builder().activityComponent(mActivityComponent).build().inject(this)
    }
}
