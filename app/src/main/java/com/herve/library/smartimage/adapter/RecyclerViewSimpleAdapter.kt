package com.herve.library.smartimage.adapter;

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
abstract class RecyclerViewSimpleAdapter(private val mContext: Context, private var mList: MutableList<Bitmap>) : androidx.recyclerview.widget.RecyclerView.Adapter<com.herve.library.smartimage.adapter.RecyclerViewSimpleAdapter.SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val itemView = getItemView(parent)
//        layoutParams.width = parent.measuredWidth / 3
//        layoutParams.height = layoutParams.width
//        itemView.layoutParams = layoutParams
        return SimpleViewHolder(itemView)
    }

    abstract fun getItemView(parent: ViewGroup): View

    final override fun getItemCount(): Int {
        return when {
            mList.size > 0 -> mList.size
            else -> placeholder()
        }
    }

    abstract fun placeholder(): Int

    final override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener(holder, it, position)
        }

        onBindView(holder.itemView, position)
    }

    abstract fun onItemClickListener(holder: SimpleViewHolder, it: View, position: Int)

    abstract fun onBindView(itemView: View, position: Int)

    class SimpleViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    }
}