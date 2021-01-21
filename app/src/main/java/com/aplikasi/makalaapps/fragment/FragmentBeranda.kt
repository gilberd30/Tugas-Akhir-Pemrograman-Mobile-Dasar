package com.aplikasi.makalaapps.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.activities.AddProductActivity
import com.aplikasi.makalaapps.activities.KeranjangActivity
import com.aplikasi.makalaapps.activities.ProfilActivity
import com.aplikasi.makalaapps.adapter.ProductAdapter
import com.aplikasi.makalaapps.modal.Product
import kotlinx.android.synthetic.main.fragment_beranda.*

class FragmentBeranda : Fragment() {

    var list = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_beranda, container, false)
        getProduct()
        return view
    }

    private fun getProduct() {
        val queue: RequestQueue = Volley.newRequestQueue(activity)
        val reques = JsonArrayRequest(Request.Method.GET,
            "http://192.168.100.17:82/store/apiproduct.php",
            null,
            { response ->
                for (s in 0..response.length() - 1) {
                    val job = response.getJSONObject(s)
                    val id = job.getInt("id")
                    val name = job.getString("name")
                    val harga = job.getInt("harga")
                    val lokasi = job.getString("lokasi")
                    val operasional = job.getString("operasional")
                    val photo = job.getString("photo").replace("localhost", "192.168.100.17")
                    val deskripsi = job.getString("deskripsi")

                    list.add(Product(id, name, harga,lokasi,operasional, deskripsi, photo))
                    val adapterku = ProductAdapter(requireContext(), list)
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                    recycler.adapter = adapterku
                }
            },
            { error ->
                Log.d("showError", error.toString())
            })
        queue.add(reques)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profil_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_profil -> {
                startActivity(Intent(activity, ProfilActivity::class.java))
                return true
            }
            R.id.action_keranjang -> {
                startActivity(Intent(activity, KeranjangActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}