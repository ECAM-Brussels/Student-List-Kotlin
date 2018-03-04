package be.ecam.lur.studentlist.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by qlurk on 26-02-18.
 */

@Entity
data class Student(@PrimaryKey val matricule: String, val name: String, val division: String)