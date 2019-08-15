package com.rc.goods.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.rc.goods.R
import com.rc.goods.model.Cart
import com.rc.goods.model.Cinema
import java.util.HashMap

class MyExpandableListViewAdapter(var context: Context) : BaseExpandableListAdapter() {

    var PList: Array<String?>? =null

    var datasets: HashMap<String, List<Cinema>>? = null

    var dates: List<Cart>? = null

    internal var totalCount = 0
    internal var totalPrice = 0.00
    val EDITING = "编辑"
    val FINISH_EDITING = "完成"

    internal var onAllCheckedBoxNeedChangeListener: OnAllCheckedBoxNeedChangeListener? = null

    //  获得某个父项的某个子项
    override fun getChild(parentPos: Int, childPos: Int): Any {
        return datasets!![PList!![parentPos]]!!.get(childPos)
    }


    fun setDate(
        mPList: Array<String?>?,
        mdatasets: HashMap<String, List<Cinema>>,
        dates: List<Cart>
    ){
        PList = mPList
        datasets = mdatasets
        this.dates = dates
        notifyDataSetChanged()

    }

    //  获得父项的数量
    override fun getGroupCount(): Int {
        if (dates == null) {
            return 0
        }

        Log.d("myex",datasets!!.size.toString())
        return dates!!.size
    }

    //  获得某个父项的子项数目
    override fun getChildrenCount(parentPos: Int): Int {
        if (this!!.dates!![parentPos].cinemas == null) {
            return 0
        }
       // return this!!.datasets!![PList!![parentPos]]!!.size
        return this!!.dates!![parentPos].cinemas.size
    }

    //  获得某个父项
    override fun getGroup(parentPos: Int): Any {
       // return this!!.datasets!![PList!![parentPos]]!!
        return this!!.dates!![parentPos]
    }

    //  获得某个父项的id
    override fun getGroupId(parentPos: Int): Long {
        return parentPos.toLong()
    }

    //  获得某个父项的某个子项的id
    override fun getChildId(parentPos: Int, childPos: Int): Long {
        return childPos.toLong()
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个函数目前一直都是返回false，没有去改动过
    override fun hasStableIds(): Boolean {
        return false
    }

    //  获得父项显示的view
    override fun getGroupView(parentPos: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_shopping_car_group, null)
        }
        var parentText = view!!.findViewById<AppCompatTextView>(R.id.tv_store_name)
        var selectCheckBox = view!!.findViewById<CheckBox>(R.id.id_cb_select_all)


        selectCheckBox.isChecked = dates!!.get(parentPos).isChecked


        selectCheckBox.setOnClickListener {



        }
        parentText!!.text = dates!![parentPos].area_name

        return view
    }

    //  获得子项显示的view
    override fun getChildView(parentPos: Int, childPos: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater =context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_shopping_car_child, null)
        }

        val text = view!!.findViewById<View>(R.id.tv_name) as AppCompatTextView
        val textView = view.findViewById<AppCompatTextView>(R.id.tv_price_key)
       // text.setText(this!!.datasets!![PList!![parentPos]]!!.get(childPos).cinema_name)
   
       // textView.setText(this!!.datasets!![PList!![parentPos]]!!.get(childPos).cinema_address)
        textView.setText("33.00")

        view.setOnClickListener {
            Toast.makeText(context, "商品：" + parentPos.toString()+childPos.toString(), Toast.LENGTH_SHORT).show()
        }

        text.setOnClickListener{

        }
        return view
    }


    interface OnAllCheckedBoxNeedChangeListener {
        fun onCheckedBoxNeedChange(allParentIsChecked: Boolean)
    }

    fun setOnAllCheckedBoxNeedChangeListener(onAllCheckedBoxNeedChangeListener: OnAllCheckedBoxNeedChangeListener) {
        this.onAllCheckedBoxNeedChangeListener = onAllCheckedBoxNeedChangeListener
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }
}