package com.aplikasi.makalaapps.modal

class Product {

    var id:Int
    var nama:String
    var harga:Int
    var lokasi:String
    var operasional:String
    var deskripsi:String
    var photo:String

    constructor(id: Int, nama: String, harga: Int,lokasi:String, operasional:String, deskripsi: String, photo: String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.lokasi = lokasi
        this.operasional = operasional
        this.deskripsi = deskripsi
        this.photo = photo
    }
}