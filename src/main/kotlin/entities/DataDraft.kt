package entities

data class DataDraft (
    var teacher: String,
    var group: String,
    var subj_name: String,
    var type_of_week: Int,
    var day_of_week: String,
    var time: Int,
    var classroom: String
)

data class DataDraftHTML (
    var group: String,
    var subj: String,
    var type_of_week: Int,
    var day_of_week: String,
    var time: Int,
)