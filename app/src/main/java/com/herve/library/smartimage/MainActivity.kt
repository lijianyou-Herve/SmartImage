package com.herve.library.smartimage

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.herve.library.smartimage.adapter.RecyclerViewSimpleAdapter
import com.herve.library.smartimage.base.BaseActivity
import com.herve.library.wedgetlib.GuideView

class MainActivity : BaseActivity() {

    lateinit var lamIvUserHead: AppCompatImageView
    lateinit var mTvUserName: AppCompatTextView
    lateinit var mTvContent: AppCompatTextView
    lateinit var mRvImages: RecyclerView
    lateinit var mTvLocation: AppCompatTextView
    lateinit var mTvTime: AppCompatTextView
    lateinit var gridLayoutManager: GridLayoutManager
    /*data*/
    private val itemLists: MutableList<String> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

        lamIvUserHead = findViewById(R.id.iv_user_head)
        mTvUserName = findViewById(R.id.tv_user_name)
        mTvContent = findViewById(R.id.tv_content)
        mRvImages = findViewById(R.id.rv_images)
        mTvLocation = findViewById(R.id.tv_location)
        mTvTime = findViewById(R.id.tv_time)

        for (value in 0..0) {
            itemLists.add("第" + value + "个动态")
        }

        gridLayoutManager = GridLayoutManager(mActivity, 1, RecyclerView.VERTICAL, false)
        mRvImages.layoutManager = gridLayoutManager
        mRvImages.adapter = object : RecyclerViewSimpleAdapter(mActivity, itemLists) {
            override fun getItemView(parent: ViewGroup): View {
                return LayoutInflater.from(mActivity).inflate(R.layout.item_image_layout, parent, false)
            }

            /**点击事件*/
            override fun onItemClickListener(holder: RecyclerViewSimpleAdapter.SimpleViewHolder, it: View?, position: Int) {
                if (itemLists.size == 9) {
                    itemLists.clear()
                }
                itemLists.add("第N个动态")
                while (itemLists.size > 4 && itemLists.size % 3 != 0) {
                    itemLists.add("第N个动态")
                }
                when {
                    itemLists.size == 1 -> gridLayoutManager.spanCount = 1
                    itemLists.size == 2 || itemLists.size == 4 -> gridLayoutManager.spanCount = 2
                    else -> gridLayoutManager.spanCount = 3
                }
                notifyDataSetChanged()
            }

            /**返回View*/
            override fun onBindView(itemView: View, position: Int) {
                if (itemView is GuideView) {

                }
                if (itemView is ImageView) {
//                    itemView.setImageResource(R.mipmap.ic_launcher)
                }
            }
        }

    }
}
