package com.herve.library.smartimage.base

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Lijianyou on 2018-09-26.
 * @author  Lijianyou
 *
 */
open abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mActivity: Activity
    protected var mToast: Toast? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        setContentView(getLayoutId())
        initView()
        initData()
        initListener()
    }

    abstract fun initData()

    abstract fun initListener()

    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutId(): Int
}