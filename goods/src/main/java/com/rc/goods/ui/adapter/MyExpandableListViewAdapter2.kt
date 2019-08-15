package com.rc.goods.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.rc.goods.R
import com.rc.goods.model.ChildEntity
import com.rc.goods.model.Cinema
import java.util.HashMap
import javax.inject.Inject

class MyExpandableListViewAdapter2(var context: Context) : BaseExpandableListAdapter() {

    private var parentText: TextView? =null
    var PList: Array<String?>? =null

    var datasets: HashMap<String, List<Cinema>>? = null

    internal var totalCount = 0
    internal var totalPrice = 0.00
    val EDITING = "编辑"
    val FINISH_EDITING = "完成"

    //  获得某个父项的某个子项
    override fun getChild(parentPos: Int, childPos: Int): Any {
        return datasets!![PList!![parentPos]]!!.get(childPos)
    }


    fun setDate(mPList: Array<String?>?,mdatasets: HashMap<String, List<Cinema>>){
        PList = mPList
        datasets = mdatasets
       // notifyDataSetChanged()

    }

    //  获得父项的数量
    override fun getGroupCount(): Int {
        if (datasets == null) {

            return 0
        }

        Log.d("myex",datasets!!.size.toString())
        return datasets!!.size
    }

    //  获得某个父项的子项数目
    override fun getChildrenCount(parentPos: Int): Int {
        if (this!!.datasets!![PList!![parentPos]] == null) {
            return 0
        }
        return this!!.datasets!![PList!![parentPos]]!!.size
    }

    //  获得某个父项
    override fun getGroup(parentPos: Int): Any {
        return this!!.datasets!![PList!![parentPos]]!!
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
            view = inflater.inflate(R.layout.elv_cinema_list_item_cityproper, null)
        }
        view!!.setTag(R.layout.elv_cinema_list_item_cityproper, parentPos)
        view.setTag(R.layout.elv_cinema_list_item_cinema, -1)
        parentText = view.findViewById<View>(R.id.parent_title) as TextView
        val parent_img = view.findViewById<ImageView>(R.id.parent_img)
        //            设置展开和收缩的文字颜色
        if (b) {
            parentText!!.setTextColor(Color.parseColor("#2FD0B5"))
            parent_img.setImageResource(R.drawable.star_yellow)
        } else {
            parentText!!.setTextColor(Color.BLACK)
            parent_img.setImageResource(R.drawable.star_grey)
        }
        parentText!!.text = PList!![parentPos]
        return view
    }

    //  获得子项显示的view
    override fun getChildView(parentPos: Int, childPos: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater =context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.elv_cinema_list_item_cinema, null)
        }
        view!!.setTag(R.layout.elv_cinema_list_item_cityproper, parentPos)
        view.setTag(R.layout.elv_cinema_list_item_cinema, childPos)
        val text = view.findViewById<View>(R.id.child_title) as TextView
        val textView = view.findViewById<TextView>(R.id.child_message)
        text.setText(this!!.datasets!![PList!![parentPos]]!!.get(childPos).cinema_name)
        textView.setText(this!!.datasets!![PList!![parentPos]]!!.get(childPos).cinema_address)
        return view
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }
}