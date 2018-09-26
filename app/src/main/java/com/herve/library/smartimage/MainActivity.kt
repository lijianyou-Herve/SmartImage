package com.herve.library.smartimage

import android.support.v7.widget.LinearLayoutManager
import com.herve.library.smartimage.adapter.MomentAdapter
import com.herve.library.smartimage.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.DividerItemDecoration



class MainActivity : BaseActivity() {

    private lateinit var momentAdapter: MomentAdapter

    /*data*/
    private val list: MutableList<String> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        momentAdapter = MomentAdapter(mActivity, list)

        rv_moment.layoutManager = LinearLayoutManager(mActivity)
        rv_moment.adapter = momentAdapter

        //添加Android自带的分割线
        rv_moment.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        for (value in 0..4) {
            list.add("第" + value + "个动态")
        }
        momentAdapter.notifyDataSetChanged()
    }
}
