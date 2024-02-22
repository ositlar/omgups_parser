package database

import entities.Data
import entities.DataDraft
import entities.DataDraftHTML
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

    fun getAllData(): List<DBDataEntityHTML> {
        return ktormDatabase.sequenceOf(DBDataTableHTML).toList()
    }

    fun getData(id: Int): DBDataEntityHTML? {
        return ktormDatabase.sequenceOf(DBDataTableHTML).firstOrNull { it.id eq id }
    }

    fun addData(draft: DataDraftHTML): Boolean {
        var insertedID: Int = 0
        try {
             insertedID = ktormDatabase.insertAndGenerateKey(DBDataTableHTML) {
                set(DBDataTableHTML.group, draft.group)
                set(DBDataTableHTML.subj, draft.subj)
                set(DBDataTableHTML.type_of_week, draft.type_of_week)
                set(DBDataTableHTML.day_of_week, draft.day_of_week)
                set(DBDataTableHTML.time, draft.time)
            } as Int

        } catch (e: Exception) {
            println(draft)
            throw e
        }
        return insertedID > 0
    }

    fun updateData(id: Int, draft: DataDraftHTML): Boolean {
        val updatedRows = ktormDatabase.update(DBDataTableHTML) {
            set(DBDataTableHTML.group, draft.group)
            set(DBDataTableHTML.subj, draft.subj)
            set(DBDataTableHTML.type_of_week, draft.type_of_week)
            set(DBDataTableHTML.day_of_week, draft.day_of_week)
            set(DBDataTableHTML.time, draft.time)
            where { it.id eq id }
        }
        return updatedRows > 0
    }

    fun removeData(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBDataTableHTML) {
            it.id eq id
        }
        return deletedRows > 0
    }

    fun removeAllData(): Boolean {
        val deletedRows = ktormDatabase.deleteAll(DBDataTableHTML)
        return deletedRows > 0
    }

}