package com.example.instargram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import kotlinx.android.synthetic.main.activity_hyung_stagram_post_list.*
import kotlinx.android.synthetic.main.activity_hyung_stagram_upload.*
import kotlinx.android.synthetic.main.activity_hyung_stagram_upload.upload
import kotlinx.android.synthetic.main.activity_hyung_stagram_user_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class HyungStagramUploadActivity : AppCompatActivity() {

    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hyung_stagram_upload)

        view_pictures.setOnClickListener {
            getPicture()
        }
        upload.setOnClickListener {
            uploadPost()
            startActivity(
                Intent(
                    this,
                    HyungStagramMyPostListActivity::class.java
                )
            )
        }

        l_user_info.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramUserInfo::class.java
                )
            )
        }

        l_all_list.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramPostListActivity::class.java
                )
            )
        }

        l_my_list1.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramMyPostListActivity::class.java
                )
            )
        }
    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val uri: Uri = data!!.data!!
            filePath = getImageFilePath(uri)
        }
    }

    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0                                          // 절대 경로를 얻기 위한 과정
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    fun uploadPost() {
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)

        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())

        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    Log.d("pathh", "" + post!!.content)
                }
            }
        })
    }

    fun getContent(): String {
        return content_input.text.toString()
    }
}