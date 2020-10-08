package creativitysol.com.planstech.stagequestions.model


import com.google.gson.annotations.SerializedName

data class QuestionsModel(
    var `data`: List<Data> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        var answers: List<Answer> = listOf(),
        var hint: String = "",
        @SerializedName("question_id")
        var questionId: Int = 0,
        var title: String = "",
        var type: String = "",
        @SerializedName("user_answer")
        var userAnswer: Any = Any()
    ) {
        data class Answer(
            var id: Int = 0,
            var value: String = ""
        )
    }
}