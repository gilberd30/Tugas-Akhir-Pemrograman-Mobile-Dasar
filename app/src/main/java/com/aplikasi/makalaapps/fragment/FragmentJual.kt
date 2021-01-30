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
import com.aplikasi.makalaapps.adapter.CategoryProduct
import com.aplikasi.makalaapps.modal.Category
import com.aplikasi.makalaapps.modal.GlobalData
import kotlinx.android.synthetic.main.fragment_jual.*

class FragmentJual : Fragment()  {
    var registerUrl: String = "http://192.168.100.17:82/store/deletejual.php"
    var list = ArrayList<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_jual, container, false)
        getCategory()
        return view
    }

    private fun getCategory(){
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonArrayRequest(Request.Method.GET,
                "http://192.168.100.17:82/store/apicategory.php?email=" + GlobalData.email,
                null,
            { response ->

                for (s in 0..response.length() - 1){
                    val job = response.getJSONObject(s)
                    val id = job.getInt("id")
                    val harga = job.getInt("harga")
                    val name = job.getString("name")
                    val photo = job.getString("photo").replace("localhost",
                            "192.168.100.17")

                    list.add(Category(id, name,harga, photo))
                    val adapterku = CategoryProduct(requireContext(), list)
                    recycler.layoutManager = LinearLayoutManager(requireContext())
                    recycler.adapter = adapterku
                }

            },
            { error ->
                Log.d("categoryEr", error.toString())
            })
        queue.add(request)
    }

    private fun delete(){
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonArrayRequest(Request.Method.GET,
                "http://192.168.100.17:82/store/apicategory.php?email=" + GlobalData.email,
                null,
                { response ->

                    for (s in 0..response.length() - 1){
                        val job = response.getJSONObject(s)
                        val id = job.getInt("id")
                        val harga = job.getInt("harga")
                        val name = job.getString("name")
                        val photo = job.getString("photo").replace("localhost",
                                "192.168.100.17")

                        list.add(Category(id, name,harga, photo))
                        val adapterku = CategoryProduct(requireContext(), list)
                        recycler.layoutManager = LinearLayoutManager(requireContext())
                        recycler.adapter = adapterku
                    }

                },
                { error ->
                    Log.d("categoryEr", error.toString())
                })
        queue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}



