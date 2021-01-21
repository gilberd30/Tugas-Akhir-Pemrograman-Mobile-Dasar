package com.aplikasi.makalaapps.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.fragment.FragmentJual
import com.aplikasi.makalaapps.modal.GlobalData
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_register.*

class AddProductActivity : BaseActivity(){
    var registerUrl: String = "http://192.168.100.17:82/store/addjualan.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        image_jual.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //showErrorSnackBar("Anda sudah memiliki izin penyimpanan.",false)
                GlobalData.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    GlobalData.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        btn_jual_sekarang.setOnClickListener {
            addMakanan()
        }

    }



    private fun validateAddMakanan(): Boolean {
        return when {
            TextUtils.isEmpty(et_nama_makanan.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_nama_makanan), true)
                false
            }
            TextUtils.isEmpty(et_harga.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_harga_makanan), true)
                false
            }
            TextUtils.isEmpty(et_deskripsi.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_deskripsi_makanan), true)
                false
            }
            TextUtils.isEmpty(et_lokasi.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_lokasi_makanan), true)
                false
            }
            TextUtils.isEmpty(et_operasional.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_jam_operasional), true)
                false
            }
            else -> {
                //showErrorSnackBar(resources.getString(R.string.registry_success),false)
                true
            }
        }
    }

    private fun addMakanan(){
        if(validateAddMakanan()){
            showProgressDialog(resources.getString(R.string.please_wait))
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)
            val strRequest = StringRequest(
                Request.Method.GET, registerUrl + "?name=" + et_nama_makanan.text.toString() + "&harga=" + et_harga.text.toString() +
                    "&deskripsi=" + et_deskripsi.text.toString() + "&lokasi=" + et_lokasi.text.toString()
                        + "&operasional=" + et_operasional.text.toString(), { response ->
                    hideProgressDialog()
                    showErrorSnackBar(resources.getString(R.string.register_success),false)
                    val i = Intent(this,BottomNavigation::class.java)
                    startActivity(i)
                    finish()
               }, { error ->
                Log.d("ErrorApps", error.toString())
            })
            request.add(strRequest)
        }
    }
}