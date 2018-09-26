package com.herve.library.smartimage.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
open abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mActivity: Activity

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        setContentView(getLayoutId())
        initView()
    }

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutId(): Int
}