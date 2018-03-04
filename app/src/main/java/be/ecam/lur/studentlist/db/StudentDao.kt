package be.ecam.lur.studentlist.db



/**
 * Created by qlurk on 26-02-18.
 */

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM Student")
    fun findAllStudents(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students: List<Student>)

    @Query("DELETE FROM Student")
    fun deleteAll()
}