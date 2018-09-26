package com.herve.library.smartimage.adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.herve.library.smartimage.R
import com.herve.library.smartimage.impl.OnAdapterItemClickListener

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
class MomentAdapter(private val mContext: Context, private var mList: MutableList<String>) : RecyclerView.Adapter<MomentAdapter.MomentViewHolder>() {
    var mOnAdapterItemClickListener: OnAdapterItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_moment_layout, parent, false)
        return MomentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            mOnAdapterItemClickListener?.onAdapterItemClickListener(holder, it, position)
//        }

        val itemLists: MutableList<String> = mutableListOf()

        for (value in 0..8) {
            itemLists.add("第" + value + "个动态")
        }

        holder.mRvImages.layoutManager = GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false)
        holder.mRvImages.adapter = object : RecyclerViewSimpleAdapter(mContext, itemLists) {
            override fun onBindView(itemView: View, position: Int) {
                if (itemView is ImageView) {
                    itemView.setImageResource(R.mipmap.ic_launcher)
                }
            }
        }
    }

    class MomentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mIvUserHead: AppCompatImageView = itemView.findViewById(R.id.iv_user_head)
        val mTvUserName: AppCompatTextView = itemView.findViewById(R.id.tv_user_name)
        val mTvContent: AppCompatTextView = itemView.findViewById(R.id.tv_content)
        val mRvImages: RecyclerView = itemView.findViewById(R.id.rv_images)
        val mTvLocation: AppCompatTextView = itemView.findViewById(R.id.tv_location)
        val mTvTime: AppCompatTextView = itemView.findViewById(R.id.tv_time)
    }

    public fun setOnAdapterItemClickListener(onAdapterItemClickListener: OnAdapterItemClickListener) {
        this.mOnAdapterItemClickListener = onAdapterItemClickListener
    }
}