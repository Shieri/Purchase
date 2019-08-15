package com.rc.goods.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.ExpandableListView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.rc.base.mvp.BaseFragment
import com.rc.base.mvp.BaseLazyFragment
import com.rc.goods.R
import com.rc.goods.contract.CartContract
import com.rc.goods.model.Cart
import com.rc.goods.model.ChildEntity
import com.rc.goods.model.Cinema
import com.rc.goods.model.ParentEntity
import com.rc.goods.presenter.CartPresenter
import com.rc.goods.ui.adapter.MyExpandableListViewAdapter
import kotlinx.android.synthetic.main.cart_frg.*
import kotlinx.android.synthetic.main.foot_layout.*
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject
import com.google.gson.Gson


class CartFragment @Inject constructor():BaseFragment(),CartContract.View{


    //定义父列表项List数据集合
    internal var parentMapList: MutableList<Map<String, Any>>? = ArrayList()
    //定义子列表项List数据集合
    internal var childMapList_list: MutableList<List<Map<String, Any>>> = ArrayList()


    @Inject
    lateinit var mPresenter: CartPresenter

    private var PList: Array<String?>? =null

    private  var  mAdapter: MyExpandableListViewAdapter? =null


    override fun injectComponent() {
        mPresenter!!.takeView(this)

    }

    override val contentView: Int
        get() = R.layout.cart_frg


    override fun initView() {
        mAdapter = MyExpandableListViewAdapter(activity as Context)
        mPresenter!!.start()

    }

    override fun onStart() {
        super.onStart()

    }



    override fun initListener() {

        /*全选*/
        id_cb_select_all.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
               // val checkBox = view as CheckBox

                mAdapter!!.datasets!!.forEach { item ->

                    Toast.makeText(mcontext,item.toString(),Toast.LENGTH_SHORT).show()
                }



            }
        })

        mExpandableListView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->


        })

    }

    override fun test(dates: List<Cart>) {
        PList = arrayOfNulls<String>(dates!!.size)
        val datasets = HashMap<String, List<Cinema>>()

        for (i in PList!!.indices) {
            PList!![i] = dates!!.get(i).area_name
            for (j in 0 until dates!!.get(i).cinemas!!.size) {
                datasets.put(PList!![i]!!,dates!!.get(i).cinemas!!)
            }
        }
        mAdapter!!.setDate(PList,datasets)

        mExpandableListView.setAdapter(mAdapter)

        for (i in 0 until mExpandableListView.count) {
            mExpandableListView.expandGroup(i)
        }


        mExpandableListView.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener{

            override fun onGroupClick(p0: ExpandableListView?, p1: View?, p2: Int, p3: Long): Boolean {

                return true
            }

        })

    }


    private fun initData() {


        for (i in 0..14) {
            //提供父列表的数据
            val parentMap = HashMap<String, Any>()
            parentMap["parentName"] = "parentName$i"
            if (i % 2 == 0) {
                parentMap["parentIcon"] = com.rc.goods.R.mipmap.iv_edit_subtract
            } else {
                parentMap["parentIcon"] = com.rc.goods.R.mipmap.iv_edit_add
            }
            parentMapList!!.add(parentMap)
            //提供当前父列的子列数据
            val childMapList = ArrayList<Map<String, Any>>()
            for (j in 0..5) {
                val childMap = HashMap<String, Any>()
                childMap["childName"] = "parentName" + i + "下面的childName" + j
                childMapList.add(childMap)
            }
            childMapList_list.add(childMapList)
        }



    }
}