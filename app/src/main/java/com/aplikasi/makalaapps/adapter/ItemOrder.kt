package com.aplikasi.makalaapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.modal.Orders
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pesan.view.*
import kotlinx.android.synthetic.main.adapter_category.view.*
import kotlinx.android.synthetic.main.adapter_orders.view.*

class ItemOrder(var context: Context, var list:ArrayList<Orders>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class myOrder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun Order(nama_product:String, photo:String, total_harga:Int){
            itemView.title_pesan.text = nama_product
            itemView.title_harga_pesan.text = total_harga.toString()
            Picasso.get().load(photo).into(itemView.image_pesan)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.adapter_orders, parent, false)
        return myOrder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //(holder as myOrder).Order(list[position].nama_product,list[position].photo,list[position].total_harga)
    }
}