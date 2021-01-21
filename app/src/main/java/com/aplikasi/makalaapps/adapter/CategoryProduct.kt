package com.aplikasi.makalaapps.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.activities.ActivityJualan
import com.aplikasi.makalaapps.activities.BaseActivity
import com.aplikasi.makalaapps.activities.BottomNavigation
import com.aplikasi.makalaapps.modal.Category
import com.aplikasi.makalaapps.modal.GlobalData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.adapter_category.view.*

class CategoryProduct (var context: Context, var list:ArrayList<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class myCategory(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun category(name:String, photo:String, harga:Int){
            itemView.title_category.text = name
            itemView.title_harga.text = "Rp " + harga.toString()
            Picasso.get().load(photo).into(itemView.image_category)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.adapter_category, parent, false)
        return myCategory(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as myCategory).category(list[position].nama, list[position].photo, list[position].harga)
        (holder as myCategory).itemView.image_category.setOnClickListener {
            val intent = Intent(context, ActivityJualan::class.java)
            GlobalData.idCategory = list[position].id
            context.startActivity(intent)

        }
        (holder as myCategory).category(list[position].nama, list[position].photo, list[position].harga)
        (holder as myCategory).itemView.delete_jualan.setOnClickListener {

        }

    }
}