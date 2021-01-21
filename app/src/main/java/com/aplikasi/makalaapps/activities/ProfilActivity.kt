package com.aplikasi.makalaapps.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.adapter.ItemOrder
import com.aplikasi.makalaapps.modal.GlobalData
import com.aplikasi.makalaapps.modal.Orders
import kotlinx.android.synthetic.main.activity_edit_profil.*
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_email
import kotlinx.android.synthetic.main.fragment_pesanan.*

class ProfilActivity : BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        tv_nama.text = "${GlobalData.firstName}  ${GlobalData.lastName}"
        tv_email.text = GlobalData.email
        tv_gender.text = GlobalData.gender
        tv_phone.text = GlobalData.phone
        tv_alamat.text = GlobalData.alamat

        tv_edit.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }

        btn_keluar.setOnClickListener {
            showProgressDialog(resources.getString(R.string.please_wait))
            userLogout()
        }
    }

    fun userLogout(){
        GlobalData.email = ""
        GlobalData.lastName = ""
        GlobalData.firstName = ""
        GlobalData.phone = ""
        GlobalData.alamat = ""
        GlobalData.gender = ""
        GlobalData.potoProfil = ""
        hideProgressDialog()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


}