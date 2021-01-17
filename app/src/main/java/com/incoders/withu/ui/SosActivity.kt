package com.incoders.withu.ui

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.incoders.withu.R
import kotlinx.android.synthetic.main.activity_sos.*
import java.util.*

class SosActivity : AppCompatActivity() {
    var lon:Double=0.0
    var lat:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)

        textView3.setOnClickListener {  val intent = Intent(this, SettingActivity::class.java)
            // start your next activity
            startActivity(intent)}
        if(Build.VERSION.SDK_INT<23)
            showLocation()
        else
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),122)

        btnStatAction.setOnClickListener {
            if(Build.VERSION.SDK_INT<23)
                sendSms()
            else
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.SEND_SMS),123)
        }

    }

    @SuppressLint("MissingPermission")
    fun showLocation(){
        // Location
        var manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                lat = location.latitude
                lon = location.longitude

                var geo = Geocoder(applicationContext, Locale.getDefault())
                var listAddress:List<Address>
                listAddress = geo.getFromLocation(lat,lon,1)
                var myLocation = listAddress[0].countryName+"\n"+ listAddress[0].locality+"\n" +
                        listAddress[0].adminArea + "\n" + listAddress[0].getAddressLine(0)

//                Toast.makeText(applicationContext,myLocation,Toast.LENGTH_LONG).show()
                textView3.text = myLocation
//                print(myLocation)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                d("onStatusChanged","onStatusChanged")
            }

            override fun onProviderEnabled(provider: String?) {
                d("onProviderEnabled","onProviderEnabled")
            }

            override fun onProviderDisabled(provider: String?) {
                d("onProviderDisabled","onProviderDisabled")
            }

        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,500f,listener)
    }

    fun sendSms(){

        //Retrieve data from SharedPref
        val myPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val sosNum =  myPref.getString("sosNumber","")
        if (sosNum == ""){
            Toast.makeText(applicationContext,"Go first to Setting and set a number",Toast.LENGTH_LONG).show()
        }else{
            val sentIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
            val deliveredIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_DELIVERED"), 0)

            var mgrSms= SmsManager.getDefault()
            mgrSms.sendTextMessage(sosNum,null,"Help, location is: \n https://maps.google.com/?q=$lat,$lon", sentIntent, deliveredIntent)

            Toast.makeText(applicationContext,"Message sent !",Toast.LENGTH_LONG).show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==122){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                showLocation()

            }
        }
        if(requestCode==123)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)

                sendSms()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.menuSetting ->{
                val intent = Intent(this, SettingActivity::class.java)
                // start your next activity
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}