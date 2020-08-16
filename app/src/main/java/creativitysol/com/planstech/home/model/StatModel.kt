package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class StatModel(
    var `data`: Data = Data(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("clients_count")
        var clientsCount: Int = 0,
        @SerializedName("cources_count")
        var courcesCount: Int = 0,
        @SerializedName("plans_count")
        var plansCount: Int = 0
    )
}