package com.aplikasi.makalaapps.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.modal.GlobalData
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_email

class RegisterActivity : BaseActivity() {

    var registerUrl: String = "http://192.168.100.17:82/store/register.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener {
            onBackPressed()
        }

        btn_register.setOnClickListener {
            registerUser()
        }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_nama_depan.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(et_nama_belakang.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }
            TextUtils.isEmpty(et_email.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_kata_sandi.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            et_kata_sandi.text.toString().trim(){ it <= ' '} != et_konfirmasi_kata_sandi.text.toString().trim(){ it <= ' '}->{
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                //showErrorSnackBar(resources.getString(R.string.registry_success),false)
                true
            }
        }
    }

    private fun registerUser(){
        if(validateRegisterDetails()){
            showProgressDialog(resources.getString(R.string.please_wait))
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)
            val strRequest = StringRequest(Request.Method.GET, registerUrl + "?first_name=" + et_nama_depan.text.toString() + "&last_name=" + et_nama_belakang.text.toString() +
                    "&email=" + et_email.text.toString() + "&password=" + et_kata_sandi.text.toString() + "&password_konfirmasi=" + et_konfirmasi_kata_sandi.text.toString(), { response ->
                if (response.equals("1")) {
                    hideProgressDialog()
                    showErrorSnackBar(resources.getString(R.string.register_success),false)
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    hideProgressDialog()
                    showErrorSnackBar(resources.getString(R.string.email_has_been_registered),true)
                }
            }, { error ->
                Log.d("ErrorApps", error.toString())
            })
            request.add(strRequest)
        }
    }
}
