package com.herve.library.smartimage.adapter;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.herve.library.smartimage.R
import com.herve.library.smartimage.impl.OnAdapterItemClickListener

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
abstract class RecyclerViewSimpleAdapter(private val mContext: Context, private var mList: MutableList<String>) : RecyclerView.Adapter<com.herve.library.smartimage.adapter.RecyclerViewSimpleAdapter.SimpleViewHolder>() {

    var mOnAdapterItemClickListener: OnAdapterItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_image_layout, parent, false)
        return SimpleViewHolder(view)
    }

    final override fun getItemCount(): Int {
        return mList.size
    }

    final override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            mOnAdapterItemClickListener?.onAdapterItemClickListener(holder, it, position)
        }

        onBindView(holder.itemView, position)
    }

    abstract fun onBindView(itemView: View, position: Int)

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    public fun setOnAdapterItemClickListener(onAdapterItemClickListener: OnAdapterItemClickListener) {
        this.mOnAdapterItemClickListener = onAdapterItemClickListener
    }
}