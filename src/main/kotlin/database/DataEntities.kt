package database

import database.DBDataTable.bindTo
import org.ktorm.entity.Entity
import org.ktorm.schema.Column
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

//Excel
object DBDataTable: Table<DBDataEntity>("new_table") {

    val id = int("id").primaryKey().bindTo { it.id }
    val teacher = varchar("teacher").bindTo { it.teacher }
    val group = varchar("group").bindTo { it.group }
    val subj_name = varchar("subj_name").bindTo { it.subj_name }
    val type_of_week = int("type_of_week").bindTo { it.type_of_week }
    val day_of_week = varchar("day_of_week").bindTo { it.day_of_week }
    val time = int("time").bindTo { it.time }
    val classroom = varchar("classroom").bindTo { it.classroom }

}

interface DBDataEntity: Entity<DBDataEntity> {
    companion object : Entity.Factory<DBDataEntity>()

    val id: Int
    val teacher: String
    val group: String
    val subj_name: String
    val type_of_week: Int
    val day_of_week: String
    val time: Int
    val classroom: String
}

//HTML
object DBDataTableHTML: Table<DBDataEntityHTML>("html_schedule") {
    val id = int("id").primaryKey().bindTo { it.id }
    val group = varchar("sgroup").bindTo { it.group }
    val subj = varchar("subj").bindTo { it.subj_name }
    val type_of_week = int("type_of_week").bindTo { it.type_of_week }
    val day_of_week = varchar("day_of_week").bindTo { it.day_of_week }
    val time = int("time").bindTo { it.time }

}

interface DBDataEntityHTML: Entity<DBDataEntityHTML> {
    companion object : Entity.Factory<DBDataEntityHTML>()

    val id: Int
    val group: String
    val subj_name: String
    val type_of_week: Int
    val day_of_week: String
    val time: Int
}