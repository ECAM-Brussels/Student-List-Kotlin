package be.ecam.lur.studentlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import be.ecam.lur.studentlist.MainActivity.ItemAdapter.ItemAdapterViewHolder
import android.view.LayoutInflater
import android.R.attr.onClick
import android.arch.lifecycle.AndroidViewModel
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import be.ecam.lur.studentlist.db.Student


class MainActivity : AppCompatActivity() {

    class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder>() {

        var students = listOf<Student>()
            set(value) {
                field = value
                this.notifyDataSetChanged()
            }

        class ItemAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var nameView: TextView = view.findViewById(R.id.student_name)
            var matriculeView: TextView = view.findViewById(R.id.student_matricule)
            var divisionView: TextView = view.findViewById(R.id.student_division)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemAdapterViewHolder {
            val context = parent?.context
            val layoutIdForListItem = R.layout.student_list_item
            val inflater = LayoutInflater.from(context)
            val shouldAttachToParentImmediately = false

            val view = inflater.inflate(layoutIdForListItem,
                    parent, shouldAttachToParentImmediately)

            return ItemAdapterViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemAdapterViewHolder?, position: Int) {
            holder?.nameView?.text = students[position].name
            holder?.matriculeView?.text = students[position].matricule
            holder?.divisionView?.text = students[position].division
        }

        override fun getItemCount(): Int {
            return students.size
        }
    }

    val itemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.setHasFixedSize(true)
        recycler.adapter = itemAdapter

        val studentModel: StudentModel = ViewModelProviders.of(this).get(StudentModel::class.java)

        //ViewModelProviders.of(this).

        val studentsObserver = Observer<List<Student>> {
            it?.let{
                itemAdapter.students = it
            }
        }

        studentModel.students.observe(this , studentsObserver)
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
