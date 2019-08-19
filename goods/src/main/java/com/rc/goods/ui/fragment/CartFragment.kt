package com.rc.goods.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ExpandableListView
import android.widget.TextView
import android.widget.Toast
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.rc.base.mvp.BaseFragment
import com.rc.goods.R
import com.rc.goods.contract.CartContract
import com.rc.goods.event.UpdateTotalPriceEvent
import com.rc.goods.model.Cart
import com.rc.goods.model.Child
import com.rc.goods.presenter.CartPresenter
import com.rc.goods.ui.adapter.MyExpandableListViewAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.cart_frg.*
import kotlinx.android.synthetic.main.foot_layout.*
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject


class CartFragment @Inject constructor():BaseFragment(),CartContract.View{

    private var mTotalPrice: Double = 0.0
    private var mTotal: Int = 0

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
    }

    override fun onStart() {
        super.onStart()

    }



    override fun initListener() {

        /*全选*/
        id_cb_select_all.setOnClickListener(object :View.OnClickListener{

            override fun onClick(view: View?) {

                mAdapter!!.dates!!.forEach{
                    it.isChecked = id_cb_select_all.isChecked
                    it.child.forEach {
                        it.isChecked = id_cb_select_all.isChecked
                    }
                }
                mAdapter!!.notifyDataSetChanged()
                updateTotalPrice()
            }
        })



        //删除按钮事件
        id_tv_delete_all.setOnClickListener {

           val list: MutableList<Child> = arrayListOf()

           val cartIdList: MutableList<Int> = arrayListOf()

            mAdapter!!.dates!!.forEach {
                list.addAll(it.child)
            }
            list.filter {it.isChecked}
                .mapTo(cartIdList) {it.id }

           if (cartIdList.size == 0) {
                toast("请选择需要删除的商品")
            }else {
               //  mPresenter.deleteCartList(cartIdList)
               cartIdList.forEach {
                   toast(it.toString())
               }

           }

        }


        titlebar_3.setListener(CommonTitleBar.OnTitleBarListener { v, action, extra ->
            val tv = v as TextView
            if (action == CommonTitleBar.ACTION_RIGHT_BUTTON || action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                if(mAdapter!!.EDITING.equals(tv.text)){
                    tv.text = mAdapter!!.FINISH_EDITING
                    id_ll_normal_all_state.visibility = View.GONE
                    id_ll_editing_all_state.visibility = View.VISIBLE

                    mAdapter!!.dates!!.forEach{
                        it.isChecked = false
                        it.child.forEach {
                            it.isChecked = false
                        }
                    }
                  mAdapter!!.notifyDataSetChanged()


                }else{
                    tv.text = mAdapter!!.EDITING
                    id_ll_normal_all_state.visibility = View.VISIBLE
                    id_ll_editing_all_state.visibility = View.GONE
                    mPresenter!!.start()
                }
            }
        })




    }



    override fun test(dates: List<Cart>) {

        mAdapter!!.setDate(dates)
        mExpandableListView.setAdapter(mAdapter)

        //更新总价
        updateTotalPrice()

        for (i in 0 until mExpandableListView.count) {
            mExpandableListView.expandGroup(i)
        }


        mExpandableListView.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener{

            override fun onGroupClick(p0: ExpandableListView?, p1: View?, p2: Int, p3: Long): Boolean {

                return true
            }

        })

    }

        /*
    更新总价
    */
    private fun updateTotalPrice() {

        mTotalPrice = 0.0
        mTotal = 0
        for (index in 0..mAdapter!!.dates!!.size-1){
            mTotalPrice += mAdapter!!.dates!![index].child.filter {
                it.isChecked
            }.map { it.count * it.price }
                .sum()

            mTotal += mAdapter!!.dates!![index].child.filter {
                it.isChecked
            }.map { it.count }
                .sum()
        }
        id_tv_totalPrice.text = mTotalPrice.toString()
        id_tv_totalCount_jiesuan.text = "去结算("+  mTotal.toString()+ ")"

    }


    /*
       注册监听
    */
    private fun initObserve() {

        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            updateTotalPrice()
        }.registerInBus(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

}