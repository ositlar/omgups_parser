package parser

import classes.Teacher
import classes.TimeTable
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun readFromExcelFile(filepath: String): MutableList<Teacher> {
    val fileInputsStream = FileInputStream(filepath)
    var xlWbs = WorkbookFactory.create(fileInputsStream)
    val tables = xlWbs.getSheetAt(0)
    val ArOfTe: MutableList<Teacher> = mutableListOf()
    val xlWs = tables
    var currentTeacher: Teacher
    var i = 0 // счетчик преподавателей
    val ListTeachers: MutableList<String> = mutableListOf()
    val ListDayClasses: MutableList<TimeTable> = mutableListOf()
    while (tables.getRow(32 * i + 6) != null) {
        currentTeacher = Teacher(
            tables.getRow(
                32 * i +
                        6
            ).getCell(1).toString(), readSchedules(i, tables)
        )
        ListTeachers.add(xlWs.getRow(32 * i + 6).getCell(1).toString())
        ArOfTe.add(currentTeacher)
        ListDayClasses.add(readSchedules(i, tables))
        i++
    }
    return ArOfTe
}