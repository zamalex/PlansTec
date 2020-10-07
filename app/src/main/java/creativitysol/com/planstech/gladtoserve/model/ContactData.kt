package creativitysol.com.planstech.gladtoserve.model


import com.google.gson.annotations.SerializedName

data class ContactData(
    var `data`: Data = Data(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        var address: String = "",
        var content: String = "",
        var email: String = "",
        var map: Map = Map(),
        @SerializedName("phone_1")
        var phone1: String = "",
        @SerializedName("phone_2")
        var phone2: String = "",
        @SerializedName("show_map")
        var showMap: Boolean = false,
        var social: Social = Social(),
        var title: String = ""
    ) {
        data class Map(
            var lat: String = "",
            var long: String = ""
        )

        data class Social(
            var facebook: String = "",
            var instagram: String = "",
            var linkedin: String = "",
            var twitter: String = "",
            var whatsapp: String = ""
        )
    }
}