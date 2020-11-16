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
        var clientsCount: Int = 0,
        @SerializedName("programs_count")
        var programsCount: Int = 0,
        @SerializedName("teams_count")
        var teamsCount: Int = 0
    )
}