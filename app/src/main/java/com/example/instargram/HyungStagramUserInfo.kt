package com.example.instargram

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hyung_stagram_post_list.*
import kotlinx.android.synthetic.main.activity_hyung_stagram_post_list.all_list
import kotlinx.android.synthetic.main.activity_hyung_stagram_post_list.my_list
import kotlinx.android.synthetic.main.activity_hyung_stagram_post_list.upload
import kotlinx.android.synthetic.main.activity_hyung_stagram_user_info.*

class HyungStagramUserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hyung_stagram_user_info)

        u_all_list.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramPostListActivity::class.java
                )
            )
        }
        u_upload.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramUploadActivity::class.java
                )
            )
        }
        u_my_list1.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HyungStagramMyPostListActivity::class.java
                )
            )
        }

        logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("login_sp", "null")
            editor.commit()

            (application as MasterApplication).createRetrofit()     // token 초기화
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }
}

