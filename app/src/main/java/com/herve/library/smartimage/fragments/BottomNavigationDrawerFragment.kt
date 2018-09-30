package com.herve.library.smartimage.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.herve.library.smartimage.R
import com.herve.library.smartimage.adapter.MenuSelectAdapter
import com.herve.library.smartimage.impl.OnAdapterItemClickListener
import kotlinx.android.synthetic.main.fragment_bottomsheet.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {


    private lateinit var menuSelectAdapter: MenuSelectAdapter
    private val mutableListOf = mutableListOf<Int>()

    companion object {
        fun getInstance() = BottomNavigationDrawerFragment()
    }

    private lateinit var mFragmentSquareCountSelectListener: FragmentSquareCountSelectListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentSquareCountSelectListener) {
            mFragmentSquareCountSelectListener = context
        } else {
            throw IllegalArgumentException("must implement FragmentSquareCountSelectListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        for (index in 1..4) {
            mutableListOf.add(index)
        }
        mutableListOf.add(6)
        mutableListOf.add(9)

        menuSelectAdapter = MenuSelectAdapter(context!!, mutableListOf)

        navigation_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        navigation_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        navigation_view.adapter = menuSelectAdapter
        navigation_view.layoutManager = GridLayoutManager(context, 3)

        menuSelectAdapter.setOnAdapterItemClickListener(object : OnAdapterItemClickListener {
            override fun onAdapterItemClickListener(viewHolder: RecyclerView.ViewHolder, view: View, ppsition: Int) {
                mFragmentSquareCountSelectListener.onSquareCountSelected(mutableListOf[ppsition])
                dismiss()
            }
        })
    }

    public fun onNavigationItemSelected(item: MenuItem): Int {
        return (item.itemId % item.groupId) - 1
    }

    public interface FragmentSquareCountSelectListener {
        fun onSquareCountSelected(squareCount: Int)
    }
}