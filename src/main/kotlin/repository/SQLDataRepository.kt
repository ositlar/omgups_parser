package repository

import database.DatabaseManager
import entities.Data
import entities.DataDraft

class SQLDataRepository : DataRepository {

    private val database = DatabaseManager()
    override fun getAllData(): List<Data> {
        return database.getAllData().map {
            Data(it.id,
                it.teacher,
                it.group,
                it.subj_name,
                it.type_of_week,
                it.day_of_week,
                it.time,
                it.classroom)
        }
    }

    override fun getData(id: Int): Data? {
        return database.getData(id) ?. let {
            Data(it.id, it.teacher, it.group, it.subj_name, it.type_of_week, it.day_of_week, it.time, it.classroom)
        }
    }

    fun getData(fullname: String): Data? {
        return database.getData(fullname) ?. let {
            Data(it.id, it.teacher, it.group, it.subj_name, it.type_of_week, it.day_of_week, it.time, it.classroom)
        }
    }

    override fun addData(draft: DataDraft): Boolean {
        return database.addData(draft)
    }

    override fun removeData(id: Int): Boolean {
        return database.removeData(id)
    }

    override fun removeAllData(): Boolean {
        return database.removeAllData()
    }

    override fun updateData(id: Int, draft: DataDraft): Boolean {
        return database.updateData(id, draft)
    }

}