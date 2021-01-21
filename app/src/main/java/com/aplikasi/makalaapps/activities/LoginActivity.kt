package com.aplikasi.makalaapps.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.adapter.ProductAdapter
import com.aplikasi.makalaapps.modal.GlobalData
import com.aplikasi.makalaapps.modal.Product
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_beranda.*

class LoginActivity : BaseActivity() {

    var url:String = "http://192.168.100.17:82/store/login.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tv_register.setOnClickListener {
            var i = Intent(this,RegisterActivity::class.java)
            startActivity(i)
        }

        btn_login.setOnClickListener {loginUser()}
    }

    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim() { it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim(){ it <= ' '})->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            else -> {
                true
            }
        }
    }

    private fun loginUser(){
        if(validateLoginDetails()){
            showProgressDialog(resources.getString(R.string.please_wait))
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)
            val stringRequest = StringRequest(
                    Request.Method.GET, url+"?email="+et_email.text.toString()+"&password="+et_password.text.toString(),
                    { response ->
                        if (response == "1"){
                            hideProgressDialog()
                            getProfil()
                            val i = Intent(this,BottomNavigation::class.java)
                            startActivity(i)
                        }else if(response == "0"){
                            hideProgressDialog()
                            showErrorSnackBar(resources.getString(R.string.account_not_found),true)
                        }
                    },
                    { error ->
                        Log.d("errorApp", error.toString());
                    })
            request.add(stringRequest)
        }
    }

    private fun getProfil() {
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val reques = JsonArrayRequest(Request.Method.GET,
                "http://192.168.100.17:82/store/apiprofil.php?email="+et_email.text.toString(),
                null,
                { response ->
                    for (s in 0..response.length() - 1) {
                        val job = response.getJSONObject(s)
                        GlobalData.firstName = job.getString("first_name")
                        GlobalData.lastName = job.getString("last_name")
                        GlobalData.email = job.getString("email")
                        GlobalData.phone = job.getString("phone")
                        GlobalData.gender = job.getString("gender")
                        GlobalData.alamat = job.getString("alamat")
                        GlobalData.potoProfil = job.getString("poto_profil").replace("localhost", "192.168.100.17")
                    }
                },
                { error ->
                    Log.d("showError", error.toString())
                })
        queue.add(reques)
    }

}