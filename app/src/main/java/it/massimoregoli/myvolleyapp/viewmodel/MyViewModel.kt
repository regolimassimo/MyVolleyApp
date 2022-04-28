package it.massimoregoli.myvolleyapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        MutableLiveData<String>("")
    }

    private val current: MutableLiveData<National> by lazy {
        MutableLiveData<National>()
    }


    fun setDate(d: String) {
        date.value = d
        if(data.value != null) {
            for (c in data.value!!) {
                if (c.data.substring(0..9) == date.value) {
                    current.value = c
                }
            }
        }
    }

    fun getData(): LiveData<List<National>> {
        return data
    }

    fun getLast(): MutableLiveData<National> {
        return current
    }

    private fun loadData(context: Context) {
        val url = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale.json"
        val latestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, { response ->
                Log.w("LATEST", response)

                val sType = object : TypeToken<List<National>>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<List<National>>(response, sType)
                data.value = mData
                current.value = mData[mData.lastIndex]
            },
            {
                Log.e("LATEST", it.message!!)
            })
        latestQueue.add(stringRequest)
    }


}