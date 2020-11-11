package creativitysol.com.planstech.stagemissions.model


import com.google.gson.annotations.SerializedName
import java.io.File

data class MissionsModel(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("answer_file")
        var answerFile: String = "",
        @SerializedName("file")
        var `file`: String ="",

        @SerializedName("hint")
        var hint: String = "",
        @SerializedName("status")
        var status: Int = 0,
        @SerializedName("task_id")
        var taskId: Int = 0,
        @SerializedName("title")
        var title: String = "",
        @SerializedName("type")
        var type: String = "",
        @SerializedName("user_answer")
        var userAnswer: String = "",


        @SerializedName("answer_note")
        var answer_note: String ="",

        @SerializedName("file_name")
        var downlaodFileName: String? = null,

        var isAnswered: Boolean = false,
        var answer: String = "",
        var selectedFile: File? = null
    )
}