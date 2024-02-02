package classes

data class Lesson(
    val group: List<String> = emptyList(),
    val classRoom: String = "",
    val name: String = "",
    val time: Int = -1
) {
    fun fullname() =
        "${group.joinToString()}\r\n  ${name.substringBefore(".")} \r\n" +
                " $classRoom "
}
