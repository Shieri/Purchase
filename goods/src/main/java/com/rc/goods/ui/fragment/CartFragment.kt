package com.rc.goods.ui.fragment

import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import com.rc.base.mvp.BaseFragment
import com.rc.goods.R
import com.rc.goods.ui.adapter.CartExpandableListAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.cart_frg.*
import javax.inject.Inject

class CartFragment @Inject constructor(): BaseFragment(){




    var i = 0

    @Inject
    lateinit var mCartExpandableListAdapter: CartExpandableListAdapter


    override val contentView: Int
        get() = R.layout.cart_frg


    override fun initView(view: View) {




    }

    override fun initListener() {

        mExpandableListView.setAdapter(mCartExpandableListAdapter)


        val intgroupCount = mExpandableListView.count


        while (i < intgroupCount) {
            mExpandableListView.expandGroup(i)
            i++
        }


        // setOnGroupClickListener listener for group heading click
        mExpandableListView.setOnGroupClickListener(ExpandableListView.OnGroupClickListener { parent, v, groupPosition, id ->

            true
        })

        // setOnChildClickListener listener for child row click
        mExpandableListView.setOnChildClickListener(ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
              Toast.makeText(activity,"OnChildClickListener",Toast.LENGTH_LONG).show()
            true
        })


        titlebar_3.setListener(CommonTitleBar.OnTitleBarListener { v, action, extra ->
            if(action == CommonTitleBar.ACTION_RIGHT_TEXT){
               if(titlebar_3.rightTextView.text.equals(mCartExpandableListAdapter.EDITING)){

                   titlebar_3.rightTextView.setText(mCartExpandableListAdapter.FINISH_EDITING)

               }else{
                   titlebar_3.rightTextView.setText(mCartExpandableListAdapter.EDITING)
               }
            }

        })


    }

    override fun initData() {

    }





}