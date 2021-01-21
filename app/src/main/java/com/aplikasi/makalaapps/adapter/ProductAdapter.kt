package com.aplikasi.makalaapps.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.activities.ActivityDetail
import com.aplikasi.makalaapps.modal.GlobalData
import com.aplikasi.makalaapps.modal.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_beranda.view.*

class ProductAdapter(var context: Context, var list:ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class myProductAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun adapter(titles:String, hargas:Int,lokasi:String,operasional:String, photos:String){

            itemView.title.text = titles
            itemView.harga.text = "Rp " + hargas.toString() + " /Porsi"
            itemView.lokasi.text = lokasi
            itemView.operasional.text = operasional
            Picasso.get().load(photos).into(itemView.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.adapter_beranda, parent, false)

        return myProductAdapter(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as myProductAdapter).adapter(list[position].nama, list[position].harga, list[position].lokasi,
            list[position].operasional, list[position].photo)
        (holder as myProductAdapter).itemView.image.setOnClickListener {
            val intent = Intent(context, ActivityDetail::class.java)
            GlobalData.ids = list[position].id
            GlobalData.names = list[position].nama
            GlobalData.hargas = list[position].harga
            GlobalData.lokasis = list[position].lokasi
            GlobalData.operasinals = list[position].operasional
            GlobalData.photos = list[position].photo
            GlobalData.deskripsis = list[position].deskripsi
            context.startActivity(intent)
        }
    }
}