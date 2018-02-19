package be.ecam.lur.studentlist

import android.arch.lifecycle.ViewModel
import okhttp3.*
import java.io.IOException
import android.arch.lifecycle.MutableLiveData




/**
 * Created by qlurk on 06-02-18.
 */
class StudentModel() : ViewModel() {
    class Student(val name: String, val matricule: String)

    private val client = OkHttpClient()

    // Create a LiveData with a String
    private val students: MutableLiveData<String> = MutableLiveData()

    fun getStudents(): MutableLiveData<String> {
        return students
    }

    init {
        students.value = "No Student"
        loadStudents()
    }

    @Throws(Exception::class)
    private fun loadStudents() {
        val request: Request = Request.Builder().url("http://calendar.ecam.be/list/e").build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                response.body()?.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code " + response)
                    students.postValue(it.string())
                }
            }
        })
    }
}