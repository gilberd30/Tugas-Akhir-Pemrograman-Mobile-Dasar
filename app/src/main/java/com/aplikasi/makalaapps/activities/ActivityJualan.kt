package com.aplikasi.makalaapps.activities


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.adapter.ProductAdapter
import com.aplikasi.makalaapps.modal.GlobalData
import com.aplikasi.makalaapps.modal.Product
import kotlinx.android.synthetic.main.fragment_beranda.*

class ActivityJualan : AppCompatActivity() {

    var list = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_beranda)

        getProduct()
    }

    private fun getProduct(){
        var queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        var reques = JsonArrayRequest(Request.Method.GET, "http://192.168.100.17:82/store/apiproductid.php?cat_id="+ GlobalData.idCategory, null,
            { response ->
                for (s in 0..response.length() - 1){
                    var job = response.getJSONObject(s)
                    var id = job.getInt("id")
                    var name = job.getString("name")
                    var harga = job.getInt("harga")
                    var lokasi = job.getString("lokasi")
                    var operasional = job.getString("operasional")
                    var photo = job.getString("photo").replace("localhost", "192.168.100.17")
                    var deskripsi = job.getString("deskripsi")
                    list.add(Product(id, name, harga,lokasi,operasional, deskripsi, photo))
                    var adapterku = ProductAdapter(applicationContext, list)
                    recycler.layoutManager = LinearLayoutManager(applicationContext)
                    recycler.adapter = adapterku
                }
            },
            { error ->
                Log.d("showError", error.toString())
            })
        queue.add(reques)
    }
}