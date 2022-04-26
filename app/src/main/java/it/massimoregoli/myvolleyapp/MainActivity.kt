package it.massimoregoli.myvolleyapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import it.massimoregoli.myvolleyapp.model.National
import it.massimoregoli.myvolleyapp.ui.theme.MyVolleyAppTheme
import it.massimoregoli.myvolleyapp.viewmodel.MyViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyVolleyAppTheme {
                val myVM: MyViewModel by viewModels()
                val last = remember { mutableStateOf(National(0,"",
                0,0,0,0,0,0))}
                myVM.getData()

                myVM.getLast().observe(this
                ) { d ->
                    last.value = d
                }


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(myVM, last)
                }


            }
        }
    }


}

@Composable
fun MyContent(
    myVM: MyViewModel,
    last: MutableState<National>
) {

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myVM.setDate("%4d-%02d-%02d".format(year, month+1, dayOfMonth))
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

        Spacer(modifier = Modifier.size(100.dp))
        if(last.value.data.length > 10)
            Text(text = "Selected Date: ${last.value.data.substring(0..9)}",
                fontSize = 30.sp,
                textAlign = TextAlign.Center)
    }
}

@Composable
fun Greeting(
    myVM: MyViewModel,
    last: MutableState<National>,
) {
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(20.dp)) {
        Card(elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = MaterialTheme.colors.surface) {
            Column(modifier = Modifier.padding(16.dp)) {
                if (last.value.data.length > 10) {
                    Text(
                        modifier = Modifier.fillMaxWidth().background(Color.White),
                        text = "%s".format(last.value.data.substring(0..9)),
                        fontSize = 24.sp,
                    textAlign = TextAlign.Center)
                }
                Text(
                    text = "Totale Positivi: %,d".format(last.value.totale_positivi),
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth().background(Color.LightGray)
                )
                Text(
                    text = "Terapia Intensiva: %,d".format(last.value.terapia_intensiva),
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth().background(Color.White)

                )
                Text(
                    text = "Ospedalizzati: %,d".format(last.value.totale_ospedalizzati),
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth().background(Color.LightGray)
                )
            }
        }
        MyContent(myVM, last)
    }
}
