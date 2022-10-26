package com.example.a645k


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AbsoluteLayout
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rs: Cursor
    lateinit var gridView: GridView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                121
            )
        }
        listImages()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 121 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            listImages()
        }
    }


    private fun listImages() {
        gridView = findViewById(R.id.gridView)
        val colmns = listOf(MediaStore.Images.Thumbnails.DATA).toTypedArray()
        rs = contentResolver.query(Media.EXTERNAL_CONTENT_URI, colmns, null, null, null)!!
        gridView.adapter = ImagesAdapter(applicationContext)
    }

    inner class ImagesAdapter : BaseAdapter {
        lateinit var context: Context

        constructor(context: Context) {
            this.context = context
        }

        override fun getCount(): Int {
            return rs.count
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val iv = ImageView(context)
            rs.moveToPosition(position)
            val path = rs.getString(0)
            val bitMap = BitmapFactory.decodeFile(path)
            iv.setImageBitmap(bitMap)
            iv.layoutParams = AbsListView.LayoutParams(300, 300)
            return iv
        }

    }
}