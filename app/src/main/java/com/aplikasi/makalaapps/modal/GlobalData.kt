package com.aplikasi.makalaapps.modal

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

class GlobalData {

    companion object{

        //user
        var firstName = String()
        var lastName = String()
        var email:String = String()
        var phone:String = String()
        var alamat :String = String()
        var gender : String = String()
        var potoProfil:String = String()

        var idCategory:Int = 0
        var ids: Int = 0;
        var names:String = String()

        //product
        var hargas:Int = 0
        var lokasis:String = String()
        var operasinals:String = String()
        var photos:String = String()
        var deskripsis:String = String()
        var catatan:String = String()
        var jumlah: Int = 0

        //paypal
        var client_id:String = "AQxKQV8Y4WHO_NCtqwQ-752W-I0db6J6tQcKt2g3FNqq31hovjmEejniQmkOwpJL5LkGvbYpw4trQp17"
        var secret:String = "EOHFhYWrTud6QEYAjZsbawoCzfZQQbH7n1qs4_s61rX8lN_e3KNr9za00B7xnMMAf-H8yBzgkmuVLYKc"

        //gambar
        var PICK_IMAGE_REQUEST_CODE = 1
        var READ_STORAGE_PERMISSION_CODE = 2

        fun showImageChooser(activity: Activity) {
            val galleryIntent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
        }
    }

}