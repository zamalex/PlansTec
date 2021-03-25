package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class StatModel(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("clients_count")
        var clientsCount: String = "",
        @SerializedName("programs_count")
        var programsCount: String = "",
        @SerializedName("teams_count")
        var teamsCount: String = ""
    )
}