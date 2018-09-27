package com.herve.library.smartimage.impl

import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
interface OnAdapterItemClickListener {
    fun onAdapterItemClickListener(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, view: View, ppsition: Int)
}