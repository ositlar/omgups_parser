package repository

import entities.Data
import entities.DataDraft
import entities.DataDraftHTML
import entities.DataHTML

interface DataRepository {
    fun getAllData(): List<DataHTML>

    fun getData(id: Int): DataHTML?

    fun addData(draft: DataDraftHTML) : Boolean

    fun removeData(id: Int): Boolean

    fun removeAllData(): Boolean

    fun updateData(id: Int, draft: DataDraftHTML): Boolean
}