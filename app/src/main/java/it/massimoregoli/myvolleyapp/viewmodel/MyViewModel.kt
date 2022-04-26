package it.massimoregoli.myvolleyapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.AndroidException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.massimoregoli.myvolleyapp.model.National

class MyViewModel(application: Application): AndroidViewModel(application) {
    private val data: MutableLiveData<List<National>> by lazy {
        MutableLiveData<List<National>>().also {
            loadData(application)
        }
    }

    private val date: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setDate(d: String) {
        date.value = d
    }

    fun getDate(): LiveData<String> {
        return date
    }

    fun getData(): LiveData<List<National>> {
        return data
    }

    private fun loadData(context: Context) {
        var url =
            "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
        url =
            "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale.json"
        val latestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, { response ->
                Log.w("LATEST", response)

                val sType = object : TypeToken<List<National>>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<List<National>>(response, sType)
                data.value = mData
            },
            {
                Log.e("LATEST", it.message!!)
            })
        latestQueue.add(stringRequest)
    }


}