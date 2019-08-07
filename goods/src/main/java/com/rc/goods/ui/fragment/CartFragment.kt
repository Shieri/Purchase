package com.rc.goods.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import com.alibaba.fastjson.JSON
import com.rc.base.mvp.BaseFragment
import com.rc.goods.R
import com.rc.goods.contract.CartContract
import com.rc.goods.model.Cart
import com.rc.goods.model.ChildEntity
import com.rc.goods.model.ParentEntity
import com.rc.goods.presenter.CartPresenter
import com.rc.goods.ui.adapter.MyExpandableListViewAdapter
import kotlinx.android.synthetic.main.cart_frg.*
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

class CartFragment @Inject constructor(): BaseFragment(),CartContract.View{

    @Inject
    lateinit var mPresenter: CartPresenter


    private var PList: Array<String?>? = null

   // private var listview: ExpandableListView? = null


    private val datasets = HashMap<String, List<ChildEntity>>()

    private  var  adapter: MyExpandableListViewAdapter? =null

    internal var parentEntities: List<ParentEntity>? =null
    internal var childEntities: List<ChildEntity>? =null
    //  internal var parentText: TextView? =null
    private var lastClick = -1//上一次点击的group的position

    override fun test(){
        Log.d("start","start()")
    }



    override fun injectComponent() {
        mPresenter!!.takeView(this)
    }






    override val contentView: Int
        get() = R.layout.cart_frg


    override fun initView(view: View) {
        initialData()


        adapter = MyExpandableListViewAdapter(activity as Context)
        adapter!!.setDate(PList,datasets)
        // adapter = MyExpandableListViewAdapter()
        mExpandableListView!!.setAdapter(adapter)





        //        Group点击事件，点击一个Group隐藏其他的(只显示一个)
        mExpandableListView!!.setOnGroupClickListener { expandableListView, view, i, l ->
            if (lastClick == -1) {
                mExpandableListView!!.expandGroup(i)
            }
            if (lastClick != -1 && lastClick != i) {
                mExpandableListView!!.collapseGroup(lastClick)
                mExpandableListView!!.expandGroup(i)
            } else if (lastClick == i) {
                if (mExpandableListView!!.isGroupExpanded(i)) {
                    mExpandableListView!!.collapseGroup(i)
                } else if (!mExpandableListView!!.isGroupExpanded(i)) {
                    mExpandableListView!!.expandGroup(i)
                }
            }
            lastClick = i
            true
        }
        //        子项点击事件
        mExpandableListView!!.setOnChildClickListener { expandableListView, view, parentPos, childPos, l ->
            /* Toast.makeText(ExpandableListViewTestActivity.this,
                        dataset.get(parentList[parentPos]).get(childPos), Toast.LENGTH_SHORT).show();*/

            true
        }

    }

    override fun initListener() {




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

    override fun initData() {



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
        PList = arrayOfNulls<String>(parentEntities!!.size)


        for (i in PList!!.indices) {
            Log.e("city", "parentEntities.size():" + parentEntities!!.size)
            PList!![i] = parentEntities!!.get(i).area_name
            Log.e("plist", "parentEntites.getName:" + parentEntities!!.get(i).area_name + "=====PList[i]" + PList!![i])
            for (j in 0 until parentEntities!!.get(i).cinemas!!.size) {
                datasets.put(PList!![i]!!,parentEntities!!.get(i).cinemas!!)

            }

        }

    }




    //val header: MutableList<String> = ArrayList()


   /// val body: MutableList<MutableList<String>> = ArrayList()


    //数据集合
   // var dataList: List<Cart> ?= null



}