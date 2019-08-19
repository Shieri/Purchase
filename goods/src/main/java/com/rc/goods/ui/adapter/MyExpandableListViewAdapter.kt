package com.rc.goods.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.eightbitlab.rxbus.Bus
import com.rc.base.ext.loadUrl
import com.rc.base.widgets.DefaultTextWatcher
import com.rc.goods.R
import com.rc.goods.event.UpdateTotalPriceEvent
import com.rc.goods.getEditText
import com.rc.goods.model.Cart
import com.rc.goods.model.Child
import ren.qinc.numberbutton.NumberButton


class MyExpandableListViewAdapter(var context: Context) : BaseExpandableListAdapter() {

    var dates: List<Cart>? = null

    internal var totalCount = 0
    internal var totalPrice = 0.00
    val EDITING = "编辑"
    val FINISH_EDITING = "完成"

    //  获得某个父项的某个子项
    override fun getChild(parentPos: Int, childPos: Int): Any {
        return this!!.dates!![parentPos].child
    }


    fun setDate(dates: List<Cart>) {
        this.dates = dates
        notifyDataSetChanged()

    }

    //  获得父项的数量
    override fun getGroupCount(): Int {
        if (dates == null) {
            return 0
        }
        return dates!!.size
    }

    //  获得某个父项的子项数目
    override fun getChildrenCount(parentPos: Int): Int {
        if (this!!.dates!![parentPos].child == null) {
            return 0
        }
        return this!!.dates!![parentPos].child.size
    }

    //  获得某个父项
    override fun getGroup(parentPos: Int): Any {
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
        var model = dates!!.get(parentPos)
        var view = view
        if (view == null) {
            val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_shopping_car_group, null)
        }
        var parentText = view!!.findViewById<AppCompatTextView>(R.id.tv_store_name)
        var selectCheckBox = view!!.findViewById<AppCompatCheckBox>(R.id.id_cb_select_all)


        selectCheckBox.isChecked = model.isChecked


        selectCheckBox.setOnClickListener {
            model.isChecked = selectCheckBox.isChecked
            model.child.forEach {
                it.isChecked = selectCheckBox.isChecked
            }
            notifyDataSetChanged()
        }

        parentText!!.text = dates!![parentPos].parentName

        return view
    }

    //  获得子项显示的view
    override fun getChildView(parentPos: Int, childPos: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {


        val model = this!!.dates!![parentPos].child[childPos]
        var view = view
        if (view == null) {
            val inflater =context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_shopping_car_child, null)
        }

        val select = view!!.findViewById<AppCompatCheckBox>(R.id.id_cb_select_child)
        val logo = view!!.findViewById<AppCompatImageView>(R.id.id_iv_logo)
        val text = view!!.findViewById<AppCompatTextView>(R.id.tv_name) as AppCompatTextView
        val textView = view.findViewById<AppCompatTextView>(R.id.tv_price_key)
        val mGoodsCountBt = view.findViewById<NumberButton>(R.id.mGoodsCountBtn)

        select.isChecked =  model.isChecked
        logo.loadUrl(model.image)
        text.setText(model.name)
        textView.setText(model.price.toString())


        mGoodsCountBt.setCurrentNumber(model.count)

        view.setOnClickListener {

        }

        select.setOnClickListener {
            model.isChecked = select.isChecked

            Log.d("dddd",select.isChecked.toString())
            notifyDataSetChanged()
            Bus.send(UpdateTotalPriceEvent())

        }

        //商品数量变化监听
        mGoodsCountBt.getEditText().addTextChangedListener(object: DefaultTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                  model.count = s.toString().toInt()
                  Bus.send(UpdateTotalPriceEvent())
            }
        })

        return view
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }
}