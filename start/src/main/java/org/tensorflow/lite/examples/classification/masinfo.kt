package org.tensorflow.lite.examples.classification

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

class masinfo : AppCompatActivity() {


    private val masInfoCode by lazy {
        findViewById<TextView>(R.id.masinfo_code)
    }

    private val pais by lazy {
        findViewById<TextView>(R.id.masinfo_pais)
    }

    private val capital by lazy {
        findViewById<TextView>(R.id.masinfo_capital)
    }

    private val imagen by lazy {
        findViewById<ImageView>(R.id.masinfo_image)
    }

    private val tel by lazy {
        findViewById<TextView>(R.id.masinfo_tel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masinfo)

        val message = intent.getStringExtra("codigo")
        masInfoCode.text = message
        getDatos(message.toString())
    }

    fun getDatos(code:String) {
        val cola = Volley.newRequestQueue(this)

        //val url = "http://www.geognos.com/api/en/countries/info/EC.json"
        val url = "https://restcountries.com/v2/alpha/$code"
//        val stringReq = StringRequest(
//            Request.Method.GET,
//            url,
//            { response ->
//                Log.d("API", response.toString())
//                                      },
//            {
//                Log.d("API", "Existe un error uuppss")
//            })

        val jsondata = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {response ->
                Log.d("API", response.toString())
                pais.text = response.getString("name")
                capital.text = response.getString("capital")
                var numeros = response.getJSONArray("callingCodes")
                tel.text = numeros.getInt(0).toString()

                val urlimg = response.getString("flag")
                var urlImgPng = response.getJSONObject("flags").getString("png")

                Log.d("IMG", urlimg)
                Log.d("IMG", urlImgPng)
                Glide.with(this)
                    .load(urlImgPng)
                    .centerCrop()
                    .into(imagen)
            },
            {
                Log.d("API", "error")
            }
        )

        cola.add(jsondata)
    }
}