package it.massimoregoli.myvolleyapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.massimoregoli.myvolleyapp.model.National
import it.massimoregoli.myvolleyapp.ui.theme.MyVolleyAppTheme
import it.massimoregoli.myvolleyapp.viewmodel.MyViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    var data: MutableList<National> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyVolleyAppTheme {

                val text = remember{ mutableStateOf("") }
                val date = remember{ mutableStateOf("") }
                val myVM: MyViewModel by viewModels()
                myVM.getData().observe(this
                ) { data ->
                    val ndx = Random().nextInt(500)
                    text.value = data[ndx].data
                }

                myVM.getDate().observe(this
                ) { d ->
                    date.value = d
                    text.value = d
                }


//                GetLatest(data, text, date)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(myVM, data, text, date)
                }


            }
        }
    }


}

@Composable
fun GetLatest(data: MutableList<National>, text: MutableState<String>, date: MutableState<String>) {
    if (data.isEmpty()) {
        var url =
            "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale-latest.json"
        url =
            "https://raw.githubusercontent.com/pcm-dpc/COVID-19/master/dati-json/dpc-covid19-ita-andamento-nazionale.json"
        val context = LocalContext.current
        val latestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET,
            url, { response ->
                Log.w("LATEST", response)

                val sType = object : TypeToken<List<National>>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<List<National>>(response, sType)
                data += mData

                text.value = data[0].data
            },
            {
                Log.e("LATEST", it.message!!)
            })
        latestQueue.add(stringRequest)
    } else {
        for(d in data) {
            if (d.data == date.value) {
                text.value = d.data
            }
        }
    }
}

@Composable
fun MyContent(
    myVM: MyViewModel,
    data: MutableList<National>,
    text: MutableState<String>,
    date: MutableState<String>
) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
//            date.value = "$mYear-${mMonth+1}-$mDayOfMonth"
            myVM.setDate("$mYear-${mMonth+1}-$mDayOfMonth")
        }, mYear, mMonth, mDay
    )

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
            Text(text = "Open Date Picker", color = Color.White)
        }

        // Adding a space of 100dp height
        Spacer(modifier = Modifier.size(100.dp))

        // Displaying the mDate value in the Text
        Text(text = "Selected Date: ${date.value}", fontSize = 30.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun Greeting(
    myVM: MyViewModel,
    data: MutableList<National>,
    name: MutableState<String>,
    date: MutableState<String>
) {
    Column {
        Text(text = name.value)
        MyContent(myVM, data, name, date)
    }
}
