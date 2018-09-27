package com.herve.library.smartimage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.herve.library.smartimage.adapter.RecyclerViewSimpleAdapter
import com.herve.library.smartimage.base.BaseActivity
import com.herve.library.smartimage.utils.BitmapSlicerManager
import com.herve.library.wedgetlib.GuideView
import java.io.File

class MainActivity : BaseActivity() {
    private val REQUEST_CODE_PICK = 0
    private val REQUEST_CODE_CUT = 1
    private val REQUEST_PERMISSION = 2

    lateinit var lamIvUserHead: AppCompatImageView
    lateinit var mTvUserName: AppCompatTextView
    lateinit var mTvContent: AppCompatTextView
    lateinit var mRvImages: androidx.recyclerview.widget.RecyclerView
    lateinit var mTvLocation: AppCompatTextView
    lateinit var mTvTime: AppCompatTextView
    lateinit var mfabSelectPicture: FloatingActionButton
    /*manager*/
    lateinit var gridLayoutManager: androidx.recyclerview.widget.GridLayoutManager
    /*data*/
    private var itemLists: MutableList<Bitmap> = mutableListOf()
    private var itemImageCount: Int = 1//图片切割的数量
    private var spanCount: Int = 1//图片切割的数量
    private var pleaseHolderCount: Int = 1//占位的图片的数量
    //    private val baseDir = Environment.getExternalStorageDirectory().toString() + "/tencent/MicroMsg/WeiXin/Slices"
    private val baseDir = Environment.getExternalStorageDirectory().toString() + "/Herve"
    private val tempFile = File(baseDir, "crop_temp.jpg")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initListener() {
        mfabSelectPicture.setOnClickListener {
            if (itemLists.size > 0) {
                recycle()
                mRvImages.adapter?.notifyDataSetChanged()
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, REQUEST_CODE_PICK)
        }
    }

    override fun initView() {
        if (!tempFile.parentFile.exists()) {
            tempFile.parentFile.mkdirs()
        }

        lamIvUserHead = findViewById(R.id.iv_user_head)
        mTvUserName = findViewById(R.id.tv_user_name)
        mTvContent = findViewById(R.id.tv_content)
        mRvImages = findViewById(R.id.rv_images)
        mTvLocation = findViewById(R.id.tv_location)
        mTvTime = findViewById(R.id.tv_time)
        mfabSelectPicture = findViewById(R.id.fab_select_picture)

    }

    override fun initData() {

        gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(mActivity, 1, androidx.recyclerview.widget.RecyclerView.VERTICAL, false)
        mRvImages.layoutManager = gridLayoutManager
        mRvImages.adapter = object : RecyclerViewSimpleAdapter(mActivity, itemLists) {
            override fun placeholder(): Int {
                return pleaseHolderCount
            }

            override fun getItemView(parent: ViewGroup): View {
                return LayoutInflater.from(mActivity).inflate(R.layout.item_image_layout, parent, false)
            }

            /**点击事件*/
            override fun onItemClickListener(holder: RecyclerViewSimpleAdapter.SimpleViewHolder, it: View, position: Int) {
                if (itemLists.size > 0) {
                    return
                }
                if (pleaseHolderCount == 9) {
                    pleaseHolderCount = 1
                } else {
                    pleaseHolderCount++
                }
                while (pleaseHolderCount > 4 && pleaseHolderCount % 3 != 0) {
                    pleaseHolderCount++
                }
                when (pleaseHolderCount) {
                    1 -> gridLayoutManager.spanCount = 1
                    2, 4 -> gridLayoutManager.spanCount = 2
                    else -> gridLayoutManager.spanCount = 3
                }

                this@MainActivity.itemImageCount = pleaseHolderCount
                this@MainActivity.spanCount = gridLayoutManager.spanCount
                notifyDataSetChanged()
            }

            /**返回View*/
            override fun onBindView(itemView: View, position: Int) {
                if (itemView is GuideView) {

                }
                if (itemView is ImageView) {
                    if (itemLists.size > 0) {
                        itemView.setImageBitmap(itemLists[position])
                    } else {
                        itemView.setImageBitmap(null)
                    }
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断是否已经赋予权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                    showToast("朕需要储存权限")
                }
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
            }
        }
    }

    fun recycle() {
        itemLists.forEach {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
        itemLists.clear()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK) {
                val uri = data?.data
                var h = 0
                var w = 0
                try {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
                    h = bitmap.height
                    w = bitmap.width
                    bitmap.recycle()
                } catch (e: Exception) {
                    e.printStackTrace()
                    showToast("无法读取图片")
                    return
                }

                val intent = Intent("com.android.camera.action.CROP")
                intent.setDataAndType(uri, "image/*")
                // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
                intent.putExtra("crop", "true")
                //该参数可以不设定用来规定裁剪区的宽高比

                intent.putExtra("aspectX", spanCount)
                intent.putExtra("aspectY", itemImageCount / spanCount)
                //该参数设定为你的imageView的大小
//                intent.putExtra("outputX", bitmapSlicer.calculateOutputX(w, h))
//                intent.putExtra("outputY", bitmapSlicer.calculateOutputY(w, h))
                //是否返回bitmap对象
                intent.putExtra("return-data", false)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
                intent.putExtra("noFaceDetection", true)
                startActivityForResult(intent, REQUEST_CODE_CUT)
            } else if (requestCode == REQUEST_CODE_CUT) {
                BitmapSlicerManager.create().setBitmapSlicerListener(object : BitmapSlicerManager.BitmapSlicerListener {
                    override fun getHorizontalPicNumber(): Int {
                        return spanCount
                    }

                    override fun getVerticalPicNumber(): Int {
                        return itemImageCount / spanCount
                    }

                    override fun onSlicerSuccess(bitmaps: MutableList<Bitmap>) {
                        recycle()
                        itemLists.addAll(bitmaps)
                        mRvImages.adapter?.notifyDataSetChanged()
                    }

                    override fun onSlicerFailed(message: String) {
                        showToast(message)
                    }

                }).doSlicer(tempFile)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    showToast("权限申请失败")
                    finish()
                    break
                }
            }
        }
    }

    @UiThread
    private fun showToast(msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT)
        } else {
            mToast?.setText(msg)
            mToast?.setDuration(Toast.LENGTH_SHORT)
        }
        mToast?.show()
    }

}
