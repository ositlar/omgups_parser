package repository

import database.DatabaseManager
import entities.Data
import entities.DataDraft
import entities.DataDraftHTML
import entities.DataHTML

class SQLDataRepository : DataRepository {

    private val database = DatabaseManager()
    override fun getAllData(): List<DataHTML> {
        return database.getAllData().map {
            DataHTML(it.id,
                it.group,
                it.subj_name,
                it.type_of_week,
                it.day_of_week,
                it.time)
        }
    }

    override fun getData(id: Int): DataHTML? {
        return database.getData(id) ?. let {
            DataHTML(it.id, it.group, it.subj_name, it.type_of_week, it.day_of_week, it.time)
        }
    }


    override fun addData(draft: DataDraftHTML): Boolean {
        return database.addData(draft)
    }

    override fun removeData(id: Int): Boolean {
        return database.removeData(id)
    }

    override fun removeAllData(): Boolean {
        return database.removeAllData()
    }

    override fun updateData(id: Int, draft: DataDraftHTML): Boolean {
        TODO("Not yet implemented")
    }

}