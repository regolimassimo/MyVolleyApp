package it.massimoregoli.myvolleyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import it.massimoregoli.myvolleyapp.ui.theme.MyVolleyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            @Composable
            fun getLatest(text: MutableState<String>) {


                val url = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
                val url2 = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale.json"
                val context = this
                val latestQueue = Volley.newRequestQueue(context)
                val stringRequest = StringRequest(Request.Method.GET,
                    url2, {response ->
                        Log.w("LATEST", response)
                        text.value = response.substring(0..100)
                    },
                    {
                        Log.e("LATEST", it.message!!)
                    })
                latestQueue.add(stringRequest)
            }

            MyVolleyAppTheme {
                var text = remember{ mutableStateOf("") }
                getLatest(text)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(text)
                }


            }
        }
    }


}

@Composable
fun Greeting(name: MutableState<String>) {
    var text = remember{ mutableStateOf("") }


    val url = "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
    val context = LocalContext.current
    val latestQueue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(Request.Method.GET,
    url, {response ->
            Log.w("LATEST", response)
            text.value = response
        },
        {
            Log.e("LATEST", it.message!!)
        })
    latestQueue.add(stringRequest)


    Text(text = name.value)
}
