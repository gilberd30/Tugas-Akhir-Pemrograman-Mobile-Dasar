package com.aplikasi.makalaapps.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.adapter.ItemOrder
import com.aplikasi.makalaapps.modal.GlobalData
import com.aplikasi.makalaapps.modal.Orders
import kotlinx.android.synthetic.main.fragment_pesanan.*
import kotlinx.android.synthetic.main.fragment_pesanan.view.*

class FragmentPesanan : Fragment() {

    var list = ArrayList<Orders>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pesanan, container, false)
        getProduct()
        return view
    }

    private fun getProduct(){
        var queue: RequestQueue = Volley.newRequestQueue(activity)
        var reques = JsonArrayRequest(Request.Method.GET,
                "http://192.168.100.17:82/store/apiorder.php?email=" + GlobalData.email, null,
            { response ->
                for (s in 0..response.length() - 1){
                    var job = response.getJSONObject(s)
                    var name_product = job.getString("nama_product")
                    var harga_total = job.getInt("total_harga")
                    val photo = job.getString("photo").replace("localhost",
                            "192.168.100.17")
                    list.add(Orders(harga_total, name_product, photo))
                    var adapterku = ItemOrder(requireContext(), list)
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                    recycler.adapter = adapterku
                }
            },
            { error ->
                Log.d("showError", error.toString())
            })
        queue.add(reques)
    }
}