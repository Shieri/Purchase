package com.rc.goods.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

import com.alibaba.fastjson.JSON
import com.library.RetrofitFactory
import com.rc.goods.R
import com.rc.goods.api.CartApi
import com.rc.goods.model.ChildEntity
import com.rc.goods.model.ParentEntity
import com.rc.goods.ui.adapter.MyExpandableListViewAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject


/**
 * Created by kehan on 16-7-19.
 */
class ExpandableListViewTestActivity : AppCompatActivity() {


    private var PList: Array<String?>? = null

    private var listview: ExpandableListView? = null


    private val datasets = HashMap<String, List<ChildEntity>>()

    private lateinit var  adapter: MyExpandableListViewAdapter


    internal var parentEntities: List<ParentEntity>? =null
    internal var childEntities: List<ChildEntity>? =null
  //  internal var parentText: TextView? =null
    private var lastClick = -1//上一次点击的group的position

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expandable_layout)
        listview = findViewById(R.id.expandablelistview) as ExpandableListView
        initialData()


        adapter = MyExpandableListViewAdapter(this@ExpandableListViewTestActivity)
        adapter!!.setDate(PList,datasets)
       // adapter = MyExpandableListViewAdapter()
        listview!!.setAdapter(adapter)





        //        Group点击事件，点击一个Group隐藏其他的(只显示一个)
        listview!!.setOnGroupClickListener { expandableListView, view, i, l ->
            if (lastClick == -1) {
                listview!!.expandGroup(i)
            }
            if (lastClick != -1 && lastClick != i) {
                listview!!.collapseGroup(lastClick)
                listview!!.expandGroup(i)
            } else if (lastClick == i) {
                if (listview!!.isGroupExpanded(i)) {
                    listview!!.collapseGroup(i)
                } else if (!listview!!.isGroupExpanded(i)) {
                    listview!!.expandGroup(i)
                }
            }
            lastClick = i
            true
        }
        //        子项点击事件
        listview!!.setOnChildClickListener { expandableListView, view, parentPos, childPos, l ->
            /* Toast.makeText(ExpandableListViewTestActivity.this,
                        dataset.get(parentList[parentPos]).get(childPos), Toast.LENGTH_SHORT).show();*/

            true
        }
    }

    private fun into() {

    }

    /**
     * 初始化数据
     */
    private fun initialData() {
        parentEntities = ArrayList<ParentEntity>()
        childEntities = ArrayList<ChildEntity>()
        val childEntity: ChildEntity
        /*json数据*/
        //        String he = "[{\"id\":117,\"area_name\":\"昌平区\",\"cinemas\":[{\"id\":\"1\",\"cinema_name\":\"DMC昌平保利影剧院\",\"cinema_address\":\"昌平区鼓楼南街佳莲时代广场4楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14205106672446.jpg\"},{\"id\":\"2\",\"cinema_name\":\"保利国际影城-龙旗广场店\",\"cinema_address\":\"昌平区黄平路19号院3号楼龙旗购物中心3楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203529883434.jpg\"}]},{\"id\":109,\"area_name\":\"朝阳区\",\"cinemas\":[{\"id\":\"7\",\"cinema_name\":\"北京剧院\",\"cinema_address\":\"朝阳区安慧里三区10号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14207675478321.jpg\"},{\"id\":\"8\",\"cinema_name\":\"朝阳剧场\",\"cinema_address\":\"朝阳区东三环北路36号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203512065410.jpg\"},{\"id\":\"10\",\"cinema_name\":\"劲松电影院\",\"cinema_address\":\"朝阳区劲松中街404号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14213800192810.jpg\"}]}]";
        val he =
            "[{\"id\":117,\"area_name\":\"昌平区\",\"cinemas\":[{\"id\":\"1\",\"cinema_name\":\"DMC昌平保利影剧院\",\"cinema_address\":\"昌平区鼓楼南街佳莲时代广场4楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14205106672446.jpg\"},{\"id\":\"2\",\"cinema_name\":\"保利国际影城-龙旗广场店\",\"cinema_address\":\"昌平区黄平路19号院3号楼龙旗购物中心3楼\",\"area_id\":\"117\",\"area_name\":\"昌平区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203529883434.jpg\"}]},{\"id\":109,\"area_name\":\"朝阳区\",\"cinemas\":[{\"id\":\"7\",\"cinema_name\":\"北京剧院\",\"cinema_address\":\"朝阳区安慧里三区10号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14207675478321.jpg\"},{\"id\":\"8\",\"cinema_name\":\"朝阳剧场\",\"cinema_address\":\"朝阳区东三环北路36号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203512065410.jpg\"},{\"id\":\"10\",\"cinema_name\":\"劲松电影院\",\"cinema_address\":\"朝阳区劲松中街404号\",\"area_id\":\"109\",\"area_name\":\"朝阳区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14213800192810.jpg\"}]},{\"id\":110,\"area_name\":\"丰台区\",\"cinemas\":[{\"id\":\"67\",\"cinema_name\":\"保利国际影城-大峡谷店\",\"cinema_address\":\"丰台区南三环西路首地大峡谷购物中心5楼\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203575287090.jpg\"},{\"id\":\"68\",\"cinema_name\":\"保利国际影城-万源店\",\"cinema_address\":\"丰台区东高地万源北路航天万源广场5楼\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"http:\\/\\/img.komovie.cn\\/cinema\\/14203530487616.jpg\"},{\"id\":\"69\",\"cinema_name\":\"恒业国际影城-北京六里桥店\",\"cinema_address\":\"北京市丰台区万丰路68号银座和谐广场购物中心5层\\t\",\"area_id\":\"110\",\"area_name\":\"丰台区\",\"logo\":\"\"}]}]"

        //        第一步首先将PList填充数据，也就是解析area_name,将json解析成List集合
        parentEntities = JSON.parseArray(he, ParentEntity::class.java)

        //        遍历集合，将area_name填充到PList集合中
        //        遍历集合，将area_name填充到PList集合中
         PList = arrayOfNulls<String>(parentEntities!!.size)


        for (i in PList!!.indices) {
            Log.e("city", "parentEntities.size():" + parentEntities!!.size)
            PList!![i] = parentEntities!!.get(i).area_name
            Log.e("plist", "parentEntites.getName:" + parentEntities!!.get(i).area_name + "=====PList[i]" + PList!![i])
            for (j in 0 until parentEntities!!.get(i).cinemas!!.size) {
                datasets.put(PList!![i]!!,parentEntities!!.get(i).cinemas!!)

            }

        }

        /* childrenList1.add(parentList[0] + "-" + "first");
        childrenList1.add(parentList[0] + "-" + "second");
        childrenList1.add(parentList[0] + "-" + "third");
        childrenList2.add(parentList[1] + "-" + "first");
        childrenList2.add(parentList[1] + "-" + "second");
        childrenList2.add(parentList[1] + "-" + "third");
        childrenList3.add(parentList[2] + "-" + "first");
        childrenList3.add(parentList[2] + "-" + "second");
        childrenList3.add(parentList[2] + "-" + "third");
        dataset.put(parentList[0], childrenList1);
        dataset.put(parentList[1], childrenList2);
        dataset.put(parentList[2], childrenList3);*/


        //        实现思路，Parent中有一个List<Child>对象，解析json的时候解析出
    }

 /*   private inner class MyExpandableListViewAdapter : BaseExpandableListAdapter() {

        //  获得某个父项的某个子项
        override fun getChild(parentPos: Int, childPos: Int): Any {
            return datasets[PList!![parentPos]]!!.get(childPos)
        }

        //  获得父项的数量
        override fun getGroupCount(): Int {
            if (datasets == null) {
                Toast.makeText(this@ExpandableListViewTestActivity, "dataset为空", Toast.LENGTH_SHORT).show()
                return 0
            }
            return datasets.size
        }

        //  获得某个父项的子项数目
        override fun getChildrenCount(parentPos: Int): Int {
            if (datasets[PList!![parentPos]] == null) {
                Toast.makeText(
                    this@ExpandableListViewTestActivity,
                    "\" + parentList[parentPos] + \" + 数据为空",
                    Toast.LENGTH_SHORT
                ).show()
                return 0
            }
            return datasets[PList!![parentPos]]!!.size
        }

        //  获得某个父项
        override fun getGroup(parentPos: Int): Any {
            return datasets[PList!![parentPos]]!!
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
                val inflater =
                    this@ExpandableListViewTestActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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
                val inflater =
                    this@ExpandableListViewTestActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.elv_cinema_list_item_cinema, null)
            }
            view!!.setTag(R.layout.elv_cinema_list_item_cityproper, parentPos)
            view.setTag(R.layout.elv_cinema_list_item_cinema, childPos)
            val text = view.findViewById<View>(R.id.child_title) as TextView
            val textView = view.findViewById<TextView>(R.id.child_message)
            text.setText(datasets[PList!![parentPos]]!!.get(childPos).cinema_name)
            textView.setText(datasets[PList!![parentPos]]!!.get(childPos).cinema_address)
            return view
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        override fun isChildSelectable(i: Int, i1: Int): Boolean {
            return true
        }
    }*/
}
