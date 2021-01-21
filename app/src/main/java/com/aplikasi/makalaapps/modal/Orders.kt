package com.aplikasi.makalaapps.modal

class Orders {

    var total_harga:Int
    var nama_product:String
    var photo:String

    constructor(total_harga: Int, nama_product: String,photo:String) {
        this.total_harga = total_harga
        this.nama_product = nama_product
        this.photo = photo
    }
}