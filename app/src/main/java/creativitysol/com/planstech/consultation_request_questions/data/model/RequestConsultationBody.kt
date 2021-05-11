package creativitysol.com.planstech.consultation_request_questions.data.model

import com.google.gson.annotations.SerializedName

data class RequestConsultationBody(

    @SerializedName("project_activity")
    val activity: Int,
    @SerializedName("note_project")
    val notes: String,
    @SerializedName("is_project")
    val isProject: Int,
    @SerializedName("have_money")
    val hasMoney: Int,
    @SerializedName("money_answer")
    val answer: String?,
    @SerializedName("consultation_method")
    val method: Int,
    @SerializedName("type_consulting_request")
    val type: Int,
    @SerializedName("consultation_date")
    val date: String,
    @SerializedName("consultation_time")
    val time: String,

    @SerializedName("slot_time_id")
    val slot: Int,
    )
