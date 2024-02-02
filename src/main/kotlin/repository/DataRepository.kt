package repository

import entities.Data
import entities.DataDraft

interface DataRepository {
    fun getAllData(): List<Data>

    fun getData(id: Int): Data?

    fun addData(draft: DataDraft) : Boolean

    fun removeData(id: Int): Boolean

    fun removeAllData(): Boolean

    fun updateData(id: Int, draft: DataDraft): Boolean
}