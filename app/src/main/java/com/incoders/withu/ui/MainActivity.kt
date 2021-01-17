package com.incoders.withu.ui


import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log.d
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.incoders.withu.R
import com.incoders.withu.util.AUDIOFILEPATH
import com.incoders.withu.util.ViewPagerAdapter
import com.incoders.withu.viewmodel.AudioViewModel
import com.incoders.withu.viewmodel.AudioViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

    lateinit var audioViewModel: AudioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = AudioViewModelFactory(application)    // Instance of viewModel factory
        audioViewModel = ViewModelProvider(this,factory).get(AudioViewModel::class.java)    // creating instance of viewModel

        viewPager.adapter =  ViewPagerAdapter(this)    // New Instance of viewPageAdapter for the viewPager

        val tabNames = listOf(R.string.tab_record, R.string.tab_audio)      // name of both the tabes

        TabLayoutMediator(tabLayout,viewPager){ tab, i ->   //Setting the tab names
            tab.text = getString(tabNames[i])
        }.attach()
    }

    // Listen after user accepts the permission of record audio
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 2){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                audioViewModel.record() // If user accepts the permission then start recording
                Handler().postDelayed({
                    uploadFIleToServer()
                }, 5000)

            }
            else{
                Toast.makeText(this,"Please Give Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun uploadFIleToServer(){
        audioViewModel.stopRecording()
        val encodedAudioFile : String
        try {
            val myUri = Uri.fromFile(File(AUDIOFILEPATH))

            val file: File = File(myUri.path)
            d("SPD","${myUri.path.toString()}")

            val requestFile = RequestBody.create(
                    MediaType.parse(getMimeType(AUDIOFILEPATH)),
                    file
            )

            val body = MultipartBody.Part.createFormData("myfile", file.name, requestFile)


            val sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(this)
            val gson = Gson()
            val json = sharedPrefs.getString("keywords", "")
            val type: Type =
                object : TypeToken<List<String?>?>() {}.type
            val arrayList: List<String> =
                gson.fromJson<List<String>>(json, type)
            val joinedarrray = arrayList.joinToString(separator = ",")
            val description = RequestBody.create(
                MultipartBody.FORM, joinedarrray)


//            val inputStream: InputStream = this@MainActivity.getContentResolver().openInputStream(myUri)!!
//            val audioInBytes: ByteArray
//            audioInBytes = ByteArray(inputStream.available())
//            inputStream.read(audioInBytes)
//            encodedAudioFile = Base64.encodeToString(audioInBytes, Base64.DEFAULT)
            audioViewModel.uploadFIleToServer(body,description)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    fun getMimeType(url: String?): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}