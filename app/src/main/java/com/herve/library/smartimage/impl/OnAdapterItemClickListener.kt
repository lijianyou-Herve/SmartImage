package com.herve.library.smartimage.impl

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
interface OnAdapterItemClickListener {
    fun onAdapterItemClickListener(viewHolder: RecyclerView.ViewHolder, view: View, ppsition: Int)
}