package creativitysol.com.planstech.follow


import com.google.gson.annotations.SerializedName

data class FollowReponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("msg")
    var msg: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("address")
        var address: String = "",
        @SerializedName("content")
        var content: String = "",
        @SerializedName("email")
        var email: String = "",
        @SerializedName("map")
        var map: Map = Map(),
        @SerializedName("phone_1")
        var phone1: String = "",
        @SerializedName("phone_2")
        var phone2: String = "",
        @SerializedName("show_map")
        var showMap: Boolean = false,
        @SerializedName("social")
        var social: Social = Social(),
        @SerializedName("title")
        var title: String = ""
    ) {
        data class Map(
            @SerializedName("lat")
            var lat: String = "",
            @SerializedName("long")
            var long: String = ""
        )

        data class Social(
            @SerializedName("facebook")
            var facebook: String = "",
            @SerializedName("instagram")
            var instagram: String = "",
            @SerializedName("linkedin")
            var linkedin: String = "",
            @SerializedName("twitter")
            var twitter: String = "",
            @SerializedName("website")
            var website: String = "",
            @SerializedName("whatsapp")
            var whatsapp: String = "",
            @SerializedName("youtube")
            var youtube: String = ""
        )
    }
}