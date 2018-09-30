package com.herve.library.smartimage.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herve.library.smartimage.R
import com.herve.library.smartimage.impl.OnAdapterItemClickListener

/**
 * Created by Lijianyou on 2018-09-30.
 * @author  Lijianyou
 *
 */
class MenuSelectAdapter(private val mContext: Context, private var mList: MutableList<Int>) : RecyclerView.Adapter<MenuSelectAdapter.MenuViewHolder>() {

    var mOnAdapterItemClickListener: OnAdapterItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_menu_layout, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            mOnAdapterItemClickListener?.onAdapterItemClickListener(holder, it, position)
        }
        if (holder.itemView is TextView) {
            holder.itemView.text = "${mList[position]} 格"
        }

    }

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    public fun setOnAdapterItemClickListener(onAdapterItemClickListener: OnAdapterItemClickListener) {
        this.mOnAdapterItemClickListener = onAdapterItemClickListener
    }
}