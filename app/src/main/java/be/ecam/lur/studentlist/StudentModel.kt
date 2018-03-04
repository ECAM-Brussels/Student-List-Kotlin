package be.ecam.lur.studentlist

import android.arch.lifecycle.AndroidViewModel
import android.app.Application
import okhttp3.*
import java.io.IOException
import be.ecam.lur.studentlist.db.AppDatabase
import org.json.JSONArray
import be.ecam.lur.studentlist.db.Student






/**
 * Created by qlurk on 06-02-18.
 */
class StudentModel(app: Application) : AndroidViewModel(app) {
    //data class Student(val name: String, val matricule: String, val division: String)

    private val client = OkHttpClient()

    // Create a LiveData with a String
    //private val students: MutableLiveData<String> = MutableLiveData()
    //val students = MutableLiveData<List<Student>>()
    val students = AppDatabase.getDatabase(getApplication()).studentDao().findAllStudents()

    /*fun getStudents(): MutableLiveData<String> {
        return students
    }*/

    init {
        //students.value = listOf()
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
                    val stuList = mutableListOf<Student>()

                    val jsonStudents = JSONArray(it.string())

                    for (i in 0 until jsonStudents.length()) {
                        val jsonStudent = jsonStudents.getJSONObject(i)
                        val name = jsonStudent.getString("npetu")
                        val matricule = jsonStudent.getString("matetu")
                        val division = jsonStudent.getString("annetu")

                        stuList.add(Student(matricule, name, division))
                    }
                    //students.postValue(stuList)
                    //AppDatabase.getDatabase(getApplication()).studentDao().deleteAll()
                    AppDatabase.getDatabase(getApplication()).studentDao().insertAll(stuList)
                }
            }
        })
    }
}