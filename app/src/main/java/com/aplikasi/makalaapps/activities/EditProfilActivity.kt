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
import com.aplikasi.makalaapps.modal.GlobalData
import kotlinx.android.synthetic.main.activity_edit_profil.*
import kotlinx.android.synthetic.main.activity_edit_profil.et_email
import kotlinx.android.synthetic.main.activity_edit_profil.iv_user_photo
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.activity_register.*

class EditProfilActivity : BaseActivity() {

    var registerUrl: String = "http://192.168.100.17:82/store/detailprofil.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        et_first_name.setText(GlobalData.firstName)
        et_last_name.setText(GlobalData.lastName)
        et_email.setText(GlobalData.email)
        et_phone.setText(GlobalData.phone)
        et_email.isEnabled = false
        if (rb_male.isChecked) {
            GlobalData.gender = "Pria"
        }else{
            GlobalData.gender = "Wanita"
        }

        iv_user_photo.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //showErrorSnackBar("Anda sudah memiliki izin penyimpanan.",false)
                GlobalData.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        GlobalData.READ_STORAGE_PERMISSION_CODE)
            }
        }

        btn_save.setOnClickListener {
            editUser()
        }
    }
    private fun editUser() {
        if (validateUserProfilDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)
            val strRequest = StringRequest(Request.Method.GET, registerUrl + "?first_name=" + et_first_name.text.toString() + "&last_name=" + et_last_name.text.toString() +
                    "&email=" + et_email.text.toString() + "&phone=" + et_phone.text.toString() + "&gender=" + GlobalData.gender, { response ->
                if (response.equals("1")) {
                    hideProgressDialog()
                    updateUserProfil()
                    showErrorSnackBar(resources.getString(R.string.register_success), false)
                    val i = Intent(this, ProfilActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }, { error ->
                Log.d("ErrorApps", error.toString())
            })
            request.add(strRequest)
        }
    }

    fun updateUserProfil() {
        GlobalData.firstName = et_first_name.text.toString()
        GlobalData.lastName = et_last_name.text.toString()
        GlobalData.email = et_email.text.toString()
        GlobalData.phone = et_phone.text.toString()
        if (GlobalData.gender == "Pria") {
            rb_male.isChecked = true
        }else{
            rb_female.isChecked = true
        }
    }

    private fun validateUserProfilDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_phone.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            else -> {
                true
            }
        }
    }
}
