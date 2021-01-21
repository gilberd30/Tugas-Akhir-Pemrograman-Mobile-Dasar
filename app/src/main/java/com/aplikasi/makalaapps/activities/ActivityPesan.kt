package com.aplikasi.makalaapps.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.aplikasi.makalaapps.R
import com.aplikasi.makalaapps.fragment.FragmentJual
import com.aplikasi.makalaapps.fragment.FragmentPesanan
import com.aplikasi.makalaapps.modal.GlobalData
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_pesan.*

import kotlinx.android.synthetic.main.activity_pesan.pesan
import kotlinx.android.synthetic.main.activity_pesan.name
import kotlinx.android.synthetic.main.activity_pesan.harga
import kotlinx.android.synthetic.main.activity_pesan.image

import kotlinx.android.synthetic.main.activity_pesan.jumlah_beli
import kotlinx.android.synthetic.main.activity_pesan.catatan
import kotlinx.android.synthetic.main.activity_register.*
import java.math.BigDecimal

class ActivityPesan : BaseActivity() {
    var registerUrl: String = "http://192.168.100.17:82/store/addorder.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)

        name.text = GlobalData.names
        harga.text = "Rp " + GlobalData.hargas.toString()

        //deskripsi.text = GlobalData.deskripsis
        Picasso.get().load(GlobalData.photos).into(image)

        var pc:PayPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.
        ENVIRONMENT_SANDBOX).clientId(GlobalData.client_id)

        val adapter = ArrayAdapter.createFromResource(this, R.array.jenis_pesanan, android.R.layout.simple_spinner_dropdown_item)
        jenis_pesan.adapter = adapter


        var i = Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, pc)
        startService(i)


        pesan.setOnClickListener {
            if(jenis_pesan.selectedItem.toString().equals("JEMPUT")){
                addOrder()
                startActivity(Intent(Intent(this,BottomNavigation::class.java)))
                showErrorSnackBar("Pesanan Sedang Di proses",false)
                }else{
                var hargaproduct = GlobalData.hargas
                var edittextHarga = jumlah_beli.text.toString()
                var catatan = catatan.text.toString()
                GlobalData.jumlah = edittextHarga.toInt()
                GlobalData.catatan = catatan.toString()
                var convertharga = edittextHarga.toInt()
                var kalikan = convertharga * hargaproduct.toInt()
                prosesPembayaran(kalikan.toString(), pc)
                Log.d("tampilkan", "${kalikan.toInt()}")
                Log.d("tampilkan", kalikan.toString())
            }
        }
    }

    fun kirimData(str:String, jml:Int, catatan:String, namas_products:String){

        var registerUrl:String = "http://192.168.100.17:82/store/historyorder.php"

        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var strRequest = StringRequest(Request.Method.GET,registerUrl+"?id_user="+str.toString()
                +"&jumlah="+jml.toInt()+
                "&catatan="+catatan.toString()+"&nama_product="+namas_products.toString(),
            { response ->

        if (response.equals("1")){
            var i = Intent(this,BottomNavigation::class.java)
            startActivity(i)
        }else{
            Toast.makeText(applicationContext, "Ada yang salah, ulangi lagi", Toast.LENGTH_LONG)
                    .show();
        }

    },
            { error ->
                Log.d("ErrorApps", error.toString())
            })

        request.add(strRequest)
    }

    fun prosesPembayaran(str:String, pcc:PayPalConfiguration) {
        val ppy: PayPalPayment = PayPalPayment(BigDecimal.valueOf(str.toString().toDouble()),
                "USD", "Makala Apps", PayPalPayment.PAYMENT_INTENT_SALE)
        val i = Intent(this, PaymentActivity::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, pcc)
        i.putExtra(PaymentActivity.EXTRA_PAYMENT, ppy)
        startActivityForResult(i, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK){
            Toast.makeText(applicationContext, "Transaksi berhasil, Terimakasih", Toast.LENGTH_LONG).show()
            kirimData(GlobalData.email, GlobalData.jumlah, GlobalData.catatan, GlobalData.names)
        }else{
            Toast.makeText(applicationContext, "Transaksi gagal", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }

    fun get_plus(view: View) {
        var jumlahItem = jumlah_beli.text.toString().toInt()
        jumlahItem++
        jumlah_beli.text = jumlahItem.toString()
        harga.text = "Rp " + (GlobalData.hargas * jumlah_beli.text.toString().toInt()).toString()
    }
    fun get_min(view: View) {
        if(jumlah_beli.text.toString().toInt() >1){
            var jumlahItem = jumlah_beli.text.toString().toInt()
            jumlahItem--
            jumlah_beli.text = jumlahItem.toString()
            harga.text = "Rp " + (GlobalData.hargas * jumlah_beli.text.toString().toInt()).toString()
        }else{
            showErrorSnackBar("Minimal Pembelian 1 item",true)
        }
    }

    fun get_keranjang(view: View) {
        startActivity(Intent(this,KeranjangActivity::class.java))
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }

    private fun addOrder(){
            showProgressDialog(resources.getString(R.string.please_wait))
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)
            val strRequest = StringRequest(
                    Request.Method.GET, registerUrl + "?nama_product=" + name.text.toString()
                    + "&total_harga=" + (GlobalData.hargas * jumlah_beli.text.toString().toInt()).toString()  + "&email=" +
                    GlobalData.email, { response ->
                hideProgressDialog()
                showErrorSnackBar(resources.getString(R.string.register_success),false)
                val i = Intent(this,BottomNavigation::class.java)
                startActivity(i)
                finish()
            }, { error ->
                Log.d("ErrorApps", error.toString())
            })
            request.add(strRequest)
        }
    }
