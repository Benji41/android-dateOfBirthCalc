package noe.basic.dateofbirthcalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var btnOpenDateDialog : Button
    lateinit var txtDate : TextView
    lateinit var txtMinutes : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnOpenDateDialog = findViewById(R.id.btn_open_dialog_date)
        txtDate = findViewById(R.id.txtDate)
        txtMinutes = findViewById(R.id.txtMinutesLived)
        btnOpenDateDialog.setOnClickListener(View.OnClickListener {
            openDatePicker()
        })
    }
    fun openDatePicker(){
        var daysL:Double =0.0
        var monthsL:Double =0.0
        var yearsL : Double =0.0
        val calendarCurrentDate = Calendar.getInstance()
        val year = calendarCurrentDate.get(Calendar.YEAR)
        val month = calendarCurrentDate.get(Calendar.MONTH)
        val day = calendarCurrentDate.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,R.style.Theme_DateOfBirthCalculator,
            DatePickerDialog.OnDateSetListener { view, y, m, d ->
                val calendarDatePicked = Calendar.getInstance()
                calendarDatePicked.set(y,m,d)

                //setting up content
                val dayFortTxt = {if(d/10 >0) d.toString() else "0$d"}
                val monthFortTxt =  {if((m+1)/10 >10)(m+1).toString() else "0${m+1}"}
                val txtDateValue = "${dayFortTxt()}/${monthFortTxt()}/$y"

                //calculate time passed
                val calculateTime = {
                    if(calendarDatePicked.time<calendarCurrentDate.time){
                        if(y == year && m == month){
                            daysL = day - d.toDouble()
                        }else if(y == year && m< month){
                            for(monthIterator in m until month){
                                calendarDatePicked.set(Calendar.MONTH,monthIterator)
                                calendarDatePicked.set(Calendar.DAY_OF_MONTH,1)
                                calendarDatePicked.set(Calendar.DATE, calendarDatePicked.getActualMaximum(Calendar.DATE));
                               if(monthIterator == m){
                                   daysL+=calendarDatePicked.getActualMaximum(Calendar.DATE)-d
                               }else{
                                   daysL+=calendarDatePicked.getActualMaximum(Calendar.DATE)
                               }
                            }
                            daysL+=day
                        }else{
                            daysL=((year-y)*365).toDouble()
                            for(monthIterator in m until month){
                                calendarDatePicked.set(Calendar.MONTH,monthIterator)
                                calendarDatePicked.set(Calendar.DAY_OF_MONTH,1)
                                calendarDatePicked.set(Calendar.DATE, calendarDatePicked.getActualMaximum(Calendar.DATE));
                                println(calendarDatePicked.time)
                                if(monthIterator == m){
                                    daysL+=calendarDatePicked.getActualMaximum(Calendar.DATE)-d
                                }else{
                                    daysL+=calendarDatePicked.getActualMaximum(Calendar.DATE)
                                }
                            }
                            daysL+=day
                        }
                }}
               calculateTime()
                //content to the view
                txtMinutes.text = "${daysL*1440}"
                txtDate.text = txtDateValue
            },year,month,day).show()

    }
}