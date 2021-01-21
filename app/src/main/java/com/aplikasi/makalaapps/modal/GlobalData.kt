package com.aplikasi.makalaapps.modal

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

class GlobalData {

    companion object{
        var client_id:String = "AVVTje67YRdSakYCrSlLC4mNiLqz0LuOq2gWSHy4Dh2tI6h7hegylVLN9lEqR0-iyKer0ZNlCwhBXkAq"
        var secret:String = "ENNPiaNBp2i77sCq7_gA1AKlul23UJwB7U6YIOHLk8DYfM2t9rczLE72S4vhE-dKzr0EIsesoY37Bb4O"

        //user
        var firstName = String()
        var lastName = String()
        var email:String = String()
        var phone:String = String()
        var alamat :String = String()
        var gender : String = String()
        var potoProfil:String = String()


        //belum
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

        //gambar
        var PICK_IMAGE_REQUEST_CODE = 1
        var READ_STORAGE_PERMISSION_CODE = 2

        fun showImageChooser(activity: Activity) {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
        }
    }

}