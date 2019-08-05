package com.rc.goods.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.rc.goods.R
import com.rc.goods.model.Cart
import com.rc.goods.model.Child
import com.rc.goods.model.Group
import kotlinx.android.synthetic.main.item_shopping_car_child.view.*
import kotlinx.android.synthetic.main.item_shopping_car_group.view.*
import javax.inject.Inject

class CartExpandableListAdapter @Inject constructor(): BaseExpandableListAdapter(){

    val EDITING = "编辑"
    val FINISH_EDITING = "完成"

    private val mcontext: Context? = null

    var groupString = arrayOf("志高冰洗旗舰店", "美的旗舰店", "海尔旗舰店", "诺基亚旗舰店")
/*
    lateinit var groupString: List<Group>
    lateinit var childString: List<Child>
*/

    var childString = arrayOf(
        arrayOf("时空房间啊链接法兰克福骄傲拉开飞机阿里进来撒劫匪了卡减肥了看见拉杀劫匪垃圾费垃圾费啦"),
        arrayOf("孙膑", "蔡文姬", "鬼谷子", "杨玉环"),
        arrayOf("张飞", "廉颇", "牛魔", "项羽")
    )

    //数据集合
    var dataList: MutableList<Cart> = mutableListOf()

    fun setData(cart: MutableList<Cart>) {
         dataList = cart
         notifyDataSetChanged()
    }

    override fun getGroup(position: Int): Any {
       return groupString?.get(position)!!
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    override fun hasStableIds(): Boolean {
       return true
    }


    // 获取显示指定分组的视图
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val groupViewHolder: GroupViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_car_group, parent, false)
            groupViewHolder = GroupViewHolder()


            //   groupViewHolder.tvTitle = convertView!!.findViewById(R.id.label_group_normal) as TextView
            groupViewHolder.tv_store_name = convertView!!.tv_store_name as TextView

            convertView.tag = groupViewHolder
        } else {
            groupViewHolder = convertView.tag as GroupViewHolder
        }
        //groupViewHolder.tv_store_name!!.text = this!!.groupString?.get(groupPosition)?.name
        groupViewHolder.tv_store_name!!.text = "eeee"
        return convertView
    }

    //获取指定分组中的子选项的个数
    override fun getChildrenCount(groupPosition: Int): Int {
     //   return childString!!.size
        return 4

    }


    //获取指定分组中的指定子选项数据
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return childString!!.get(groupPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    //取得显示给定分组给定子位置的数据用的视图
    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val childViewHolder: ChildViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_car_child, parent, false)

            childViewHolder = ChildViewHolder()
            childViewHolder.tvName = convertView!!.tv_name as TextView


            convertView.tag = childViewHolder

        } else {
            childViewHolder = convertView.tag as ChildViewHolder
        }
       // childViewHolder.tvName!!.text =this!!.childString?.get(groupPosition)?.goodsDesc
        childViewHolder.tvName!!.text = "this"
        return convertView
    }

    //获取子选项的ID, 这个ID必须是唯一的
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {


        return 4

    }



    internal class GroupViewHolder {
        //      var tvTitle: TextView? = null
        var tv_store_name: TextView? = null
    }

    internal class ChildViewHolder {
        // var tvTitle: TextView? = null
        var tvName: TextView? = null

    }

    fun setupAllChecked(isChecked:Boolean){

    }


}