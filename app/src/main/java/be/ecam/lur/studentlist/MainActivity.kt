package be.ecam.lur.studentlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val studentModel: StudentModel = ViewModelProviders.of(this).get(StudentModel::class.java)

        val studentsObserver = Observer<String> {
            display.text = it
        }

        studentModel.getStudents().observe(this , studentsObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var itemId = item?.itemId
        if (itemId == R.id.action) {
            Toast.makeText(this, "Download initiated", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


}
