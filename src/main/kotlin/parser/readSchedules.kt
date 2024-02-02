package parser

import classes.Day
import classes.Lesson
import classes.TimeTable
import org.apache.poi.ss.usermodel.Sheet
import regexp

fun readSchedules(idTeacher: Int, table: Sheet): TimeTable// возвращает расписание для преподавателя
{
    val timeTable = TimeTable(
        mutableListOf(),
        mutableListOf()
    )
    val start = 32 * idTeacher + 8 //Начальная строчка
    var currentLesson: Lesson?// Содержит в себе текущую пару
    var Row = start //текущая строка
    var Cell = 1 // Текущий день недели или начальный столбец
    var ThisDayOne: Day // Нечентный день
    var ThisDaySecond: Day // Четный день
    val NextRow = Row + 19 // Следующая
    val PastCell = 6 // Последни стоблец
    var group: List<String>
    var classRoom: String
    var name: String
    var time: Int = -1

    while (Cell <= PastCell) {
        ThisDayOne = Day(
            when (Cell) {
                1 -> "Понедельник"
                2 -> "Вторник"
                3 -> "Среда"
                4 -> "Четверг"
                5 -> "Пятница"
                6 -> "Суббота"
                else -> ""
            }, mutableListOf()
        )
        ThisDaySecond = Day(
            ThisDayOne.dayOfWeek, mutableListOf()
        )
        while (Row <= NextRow) {
            try {
                if(Row % 4 == 0){
                    time = getNumberOfLesson(table.getRow(Row).getCell(0).toString())
                }
                if (table.getRow(Row).getCell(Cell).toString().isBlank() && table.getRow(Row + 1).getCell(Cell).toString().isBlank()
                ) {
                    group = regexp.findAll(table.getRow(Row).getCell(Cell).toString()).map {
                        it.value    //NullPointerException
                    }.toList()
                    classRoom = table.getRow(Row).getCell(Cell).toString()
                    name = table.getRow(Row + 1).getCell(Cell).toString()
                    currentLesson = Lesson(group, classRoom, name, time)
                    //Row += 2
                    if (Row % 4 != 0)
                        ThisDayOne.classes += currentLesson
                    else
                        ThisDaySecond.classes += currentLesson
                    Row += 2

                } else if (table.getRow(Row).getCell(Cell).toString().isBlank() && table.getRow(Row + 1).getCell(Cell)
                        .toString().isNotBlank()
                ) {
                    currentLesson = getLesson(table, Row + 1, Cell)
                    currentLesson = currentLesson.copy(time = time)
                    ThisDayOne.classes += currentLesson
                    ThisDaySecond.classes += currentLesson
                    Row += 4
                } else if (table.getRow(Row).getCell(Cell).toString() != "") {

                    currentLesson = getLesson(table, Row, Cell)
                    currentLesson = currentLesson.copy(time = time)
                    if (Row % 4 != 0)
                        ThisDayOne.classes += currentLesson
                    else
                        ThisDaySecond.classes += currentLesson
                    Row += 2
                } else {
                    Row += 2
                }
            } catch (error: NullPointerException) {
                println(error.cause)
            }
        }
        timeTable.upWeek += ThisDaySecond
        timeTable.lowWeek += ThisDayOne
        Cell++
        Row = start
    }
    return timeTable
}

fun getLesson(table: Sheet, Row: Int, Cell: Int): Lesson {
    val group = regexp.findAll(table.getRow(Row).getCell(Cell).toString().substringBefore(" ")).map {
        it.value    //NullPointerException
    }.toList()

    val classRoom = table.getRow(Row).getCell(Cell).toString().substringAfter("а.")
    val name = table.getRow(Row + 1).getCell(Cell).toString()
    return Lesson(group, classRoom, name)
}

fun getNumberOfLesson(time: String): Int {
    val times = mapOf(
        "08.00-09.35" to 0,
        "09.45-11.20" to 1,
        "11.30-13.05" to 2,
        "13.55-15.30" to 3,
        "15.40-17.15" to 4
    )
    return times[time] ?: -1
}