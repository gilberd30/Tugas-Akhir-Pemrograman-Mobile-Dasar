package com.aplikasi.makalaapps.modal

class Category {

    var id:Int
    var nama:String
    var harga: Int
    var photo:String

    constructor(id: Int, nama: String,harga:Int, photo: String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.photo = photo
    }
}