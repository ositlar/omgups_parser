@file:JvmName("MainKt")
@file:Suppress("CAST_NEVER_SUCCEEDS")

import entities.DataDraft
import parser.excel.readFromExcelFile
import parser.html.parse
import repository.DataRepository
import repository.SQLDataRepository

val regexp = Regex("(\\d{2}[а-я])+")
val db: DataRepository = SQLDataRepository()
fun main(args: Array<String>) {
    val listOfFiles: List<String> = listOf(
        "C:/Users/Максим/Documents/parser/src/main/resources/aisu.xlsx",
        "C:/Users/Максим/Documents/parser/src/main/resources/ait.xlsx",
        "C:/Users/Максим/Documents/parser/src/main/resources/bz.xlsx"
    )
    val pairs = parse()

    //initial clear database
    db.removeAllData()

    //Insert new data
    pairs.forEach { pair ->
        db.addData(pair)
    }

    //Getting raw data in class-tree
//    listOfFiles.forEach { path ->
//
//
//        val teachersTimeTable = readFromExcelFile(
//            path
//        )
//        //inserting into mysql
//        teachersTimeTable.forEach { teacher ->
//            teacher.table.lowWeek.forEach { day ->
//                day.classes.forEach { lesson ->
//                    lesson.group.forEach { group ->
//                        db.addData(
//                            DataDraft(
//                                teacher = teacher.fullname,
//                                group = group,
//                                subj_name = lesson.name,
//                                type_of_week = 0,
//                                day_of_week = day.dayOfWeek,
//                                time = lesson.time,
//                                classroom = lesson.classRoom
//                            )
//                        )
//                    }
//                }
//            }
//            teacher.table.upWeek.forEach { day ->
//                day.classes.forEach { lesson ->
//                    lesson.group.forEach { group ->
//                        db.addData(
//                            DataDraft(
//                                teacher = teacher.fullname,
//                                group = group,
//                                subj_name = lesson.name,
//                                type_of_week = 1,
//                                day_of_week = day.dayOfWeek,
//                                time = lesson.time,
//                                classroom = lesson.classRoom
//                            )
//                        )
//
//                    }
//
//                }
//            }
//        }
//    }
}