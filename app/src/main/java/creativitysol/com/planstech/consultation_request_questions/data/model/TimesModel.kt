package creativitysol.com.planstech.consultation_request_questions.data.model


import com.google.gson.annotations.SerializedName

data class TimesModel(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("slots")
    var slots: List<Slot> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Slot(
        @SerializedName("from")
        var from: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("to")
        var to: String = ""
    )
}