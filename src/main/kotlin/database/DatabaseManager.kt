package database

import entities.Data
import entities.DataDraft
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.expression.SqlExpression
import org.ktorm.expression.SqlFormatter
import org.ktorm.support.mysql.MySqlFormatter
import java.sql.SQLException

class DatabaseManager {

    //config
    private val hostname = "127.0.0.1"
    private val databaseName = "parsed_data"
    private val username = "bestuser"
    private val password = "bestuser"

    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getAllData(): List<DBDataEntity> {
        return ktormDatabase.sequenceOf(DBDataTable).toList()
    }

    fun getData(id: Int): DBDataEntity? {
        return ktormDatabase.sequenceOf(DBDataTable).firstOrNull { it.id eq id }
    }

    fun getData(fullname: String): DBDataEntity? {
        return ktormDatabase.sequenceOf(DBDataTable).firstOrNull { it.teacher eq fullname }
    }

    fun addData(draft: DataDraft): Boolean {
        val insertedID = ktormDatabase.insertAndGenerateKey(DBDataTable) {
            set(DBDataTable.teacher, draft.teacher)
            set(DBDataTable.group, draft.group)
            set(DBDataTable.subj_name, draft.subj_name)
            set(DBDataTable.type_of_week, draft.type_of_week)
            set(DBDataTable.day_of_week, draft.day_of_week)
            set(DBDataTable.time, draft.time)
        } as Int
        return insertedID > 0
    }

    fun updateData(id: Int, draft: DataDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBDataTable) {
            set(DBDataTable.teacher, draft.teacher)
            set(DBDataTable.group, draft.group)
            set(DBDataTable.subj_name, draft.subj_name)
            set(DBDataTable.type_of_week, draft.type_of_week)
            set(DBDataTable.day_of_week, draft.day_of_week)
            set(DBDataTable.time, draft.time)
            where { it.id eq id }
        }
        return updatedRows > 0
    }

    fun removeData(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBDataTable) {
            it.id eq id
        }
        return deletedRows > 0
    }

    fun removeAllData(): Boolean {
        val deletedRows = ktormDatabase.deleteAll(DBDataTable)
        return deletedRows > 0
    }

}