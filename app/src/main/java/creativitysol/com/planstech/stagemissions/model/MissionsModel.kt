package creativitysol.com.planstech.stagemissions.model


import com.google.gson.annotations.SerializedName
import java.io.File

data class MissionsModel(
    var `data`: List<Data> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        var task_id: Int,
        var `file`: Any = Any(),
        var hint: String = "",
        var title: String = "",
        var type: String = "",
        @SerializedName("user_answer_text")
        var userAnswerText: Any = Any(),

        var isAnswered: Boolean = false,
        var answer: String = "",
        var selectedFile: File? = null
    )
}