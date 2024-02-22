package parser.html

import entities.DataDraft
import entities.DataDraftHTML
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

val addr = "C:/Users/Максим/Documents/parser/src/main/resources/html/"


val days = mapOf<Int, String>(
    0 to "Понедельник",
    1 to "Вторник",
    2 to "Среда",
    3 to "Четверг",
    4 to "Пятница",
    5 to "Суббота"
)
fun parse(): List<DataDraftHTML> {
    val sourceDir = File(addr)
    val mutList: MutableList<DataDraftHTML> = mutableListOf()
    sourceDir.listFiles()?.forEach { facDir ->
        facDir.listFiles()?.forEach { groupFile ->
            val htmlData = Jsoup.parse(groupFile)
            val group = htmlData.select("p")[0].text().substringAfter(": ")
            val table = htmlData.select("table")
            for (rowIter in 2 until 14) {
                val day = days[(rowIter-2) % 6]
                val weekType = if (rowIter > 6) {
                    1
                } else {
                    0
                }
                for (coll in 1 until 6) {
                    val time = coll-1
                    val cell = table.select("tr")[rowIter].select("td")[coll]
                    //val extractedData = extractSubject(cell.text())
                    val pair = DataDraftHTML (
                        group = group,
                        day_of_week = day!!,
                        subj = cell.text(),
                        type_of_week = weekType,
                        time = time
                    )
                    mutList.add(pair)
                    /*
                    if (extractedData.size == 4) {
                        val pair = DataDraft(
                            group = group,
                            day_of_week = day!!,
                            subj_name = extractedData[0] + "." + extractedData[3],
                            type_of_week = weekType,
                            classroom = extractedData[1],
                            teacher = extractedData[2],
                            time = time
                        )
                        println(pair)
                        mutList.add(
                            pair
                        )
                    } else {
                        val pair1 = DataDraft(
                            group = group,
                            day_of_week = day!!,
                            subj_name = extractedData[0] + "." + extractedData[3],
                            type_of_week = weekType,
                            classroom = extractedData[1],
                            teacher = extractedData[2],
                            time = time
                        )
                        print(pair1)
                        println("(pair1)")
                        val pair2 = DataDraft(
                            group = group,
                            day_of_week = day,
                            subj_name = extractedData[4] + "." + extractedData[7],
                            type_of_week = weekType,
                            classroom = extractedData[5],
                            teacher = extractedData[6],
                            time = time
                        )
                        print(pair2)
                        println("(pair2)")
                        mutList.add(
                            pair1
                        )
                        mutList.add(
                            pair2
                        )
                    }

                     */
                }
            }
        }
    }
    return mutList
}

/*
0 - тип предмета (лекция. практика, лаба ...)
1 - аудитория
2 - преподаватель
3 - название предмета

Вложенные значения в расписании
4 - тип предмета
5 - аудитория
6 - преподаватель
7 - название предмета
 */
fun extractSubject(cell: String): Array<String> {
    if (cell.contains("Физкультура")) {
        val classroom = cell.substringAfter("a.")
        return (arrayOf("пр", classroom, "_", "Физкультура"))
    }
    val subjectType = cell.substringBefore(".")
    var classroom = cell.substringAfter("а.").substringBefore(" ")
//    if (classroom.length > 5) {
//        classroom = classroom.substringBefore(" ")
//    }
    val innerPair: MutableList<String> = mutableListOf()
    val innerClassroom = cell.substringAfter("a.")
//    if (innerClassroom.length > 5) {
//        val innerClassroomPair = InnerInClassroom(innerClassroom.substringAfter(" "))
//        println("Inner pair: " + innerClassroomPair[0] + " " + innerClassroomPair[1] + " " + innerClassroomPair[2] + " " + innerClassroomPair[3] + " ")
//        innerPair.addAll(innerClassroomPair)
//    }
    if (cell.contains("доц.")) {
        val teacher = cell.substringAfter("доц.").substringBefore("а.")
        val subject = cell.substringBefore("доц.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
        if (innerPair.isEmpty()) {
            return arrayOf(subjectType, classroom, teacher, subject)
        } else {
            return arrayOf(subjectType, classroom, teacher, subject) + innerPair
        }

    } else if (cell.contains("проф.")) {
        val teacher = cell.substringAfter("проф.").substringBefore("а.")
        val subject = cell.substringBefore("проф.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
        if (innerPair.isEmpty()) {
            return arrayOf(subjectType, classroom, teacher, subject)
        } else {
            return arrayOf(subjectType, classroom, teacher, subject) + innerPair
        }
    } else if (cell.contains("ст.пр.")) {
        val teacher = cell.substringAfter("ст.пр.").substringBefore("а.")
        val subject = cell.substringBefore("ст.пр.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
        if (innerPair.isEmpty()) {
            return arrayOf(subjectType, classroom, teacher, subject)
        } else {
           return arrayOf(subjectType, classroom, teacher, subject) + innerPair
        }
    } else if (cell.contains("ГОЛОВИН Д.В.")) {
        val teacher = "ГОЛОВИН Д.В."
        val subject = cell.substringBefore("ГОЛОВИН Д.В.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
        if (innerPair.isEmpty()) {
            return arrayOf(subjectType, classroom, teacher, subject)
        } else {
            return arrayOf(subjectType, classroom, teacher, subject) + innerPair
        }
    } else if (cell.contains("Физкультура а.Сз13_")) {
        return (arrayOf("пр.", "Сз.17", "_", "Физкультура"))
    } else {
        return arrayOf("_", "_", "_", "_")
    }
}

fun InnerInClassroom(cell: String): Array<String> {
    val subjectType = cell.substringBefore(".")
    var classroom = cell.substringAfter("а.").substringBefore(" ")
    if (cell.contains("доц.")) {
        val teacher = cell.substringAfter("доц.").substringBefore("а.")
        val subject = cell.substringBefore("доц.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
            return arrayOf(subjectType, classroom, teacher, subject)

    } else if (cell.contains("проф.")) {
        val teacher = cell.substringAfter("проф.").substringBefore("а.")
        val subject = cell.substringBefore("проф.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
            return arrayOf(subjectType, classroom, teacher, subject)
    } else if (cell.contains("ст.пр.")) {
        val teacher = cell.substringAfter("ст.пр.").substringBefore("а.")
        val subject = cell.substringBefore("ст.пр.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
            return arrayOf(subjectType, classroom, teacher, subject)
    } else if (cell.contains("ЛИТВИНОВА О.В.")) {
        val teacher = "ЛИТВИНОВА О.В."
        val subject = cell.substringBefore("ЛИТВИНОВА О.В.").substringAfter(".")
            .substringBefore("- 1").substringBefore("- 2")
            return arrayOf(subjectType, classroom, teacher, subject)
    } else {
        return arrayOf("_", "_", "_", "_")
    }
}