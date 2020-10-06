package creativitysol.com.planstech.stages.model


import com.google.gson.annotations.SerializedName

data class StagesModel(
    var `data`: List<Data> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("can_subscribe")
        var canSubscribe: Int = 0,
        @SerializedName("is_passed")
        var isPassed: Int = 0,
        @SerializedName("is_subscribed")
        var isSubscribed: Int = 0,
        @SerializedName("package_title")
        var packageTitle: String = "",
        @SerializedName("stage_id")
        var stageId: Int = 0,
        @SerializedName("stage_title")
        var stageTitle: String = "",
        @SerializedName("task_id")
        var taskId: Int = 0
    )
}