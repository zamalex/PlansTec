package creativitysol.com.planstech.conschat.data.model


import com.google.gson.annotations.SerializedName

data class ChatMessages(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    @SerializedName("success")
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("channel_id")
        var channelId: String = "",
        @SerializedName("conversation_id")
        var conversationId: Int = 0,
        @SerializedName("messages")
        var messages: List<Message> = listOf(),
        @SerializedName("other_user")
        var otherUser: OtherUser = OtherUser(),
        @SerializedName("status")
        var status: String = ""
    ) {
        data class Message(
            @SerializedName("conversation_id")
            var conversationId: String = "",
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("deleted_from_receiver")
            var deletedFromReceiver: Int = 0,
            @SerializedName("deleted_from_sender")
            var deletedFromSender: Int = 0,
            @SerializedName("humans_time")
            var humansTime: String = "",
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("is_seen")
            var isSeen: Int = 0,
            @SerializedName("message")
            var message: String = "",
            @SerializedName("sender")
            var sender: Sender = Sender(),
            @SerializedName("updated_at")
            var updatedAt: String = "",
            @SerializedName("user_id")
            var userId: String = ""
        ) {
            data class Sender(
                @SerializedName("avatar")
                var avatar: String = "",
                @SerializedName("city")
                var city: String = "",
                @SerializedName("created_at")
                var createdAt: String = "",
                @SerializedName("district")
                var district: String = "",
                @SerializedName("email")
                var email: String = "",
                @SerializedName("email_verified_at")
                var emailVerifiedAt: Any = Any(),
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("is_verified")
                var isVerified: Int = 0,
                @SerializedName("name")
                var name: String = "",
                @SerializedName("phone")
                var phone: Any = Any(),
                @SerializedName("role")
                var role: String = "",
                @SerializedName("subscription_plans")
                var subscriptionPlans: Int = 0,
                @SerializedName("support_chat")
                var supportChat: Int = 0,
                @SerializedName("updated_at")
                var updatedAt: String = "",
                @SerializedName("usertype")
                var usertype: String = "",
                @SerializedName("verification_code")
                var verificationCode: String = ""
            )
        }

        data class OtherUser(
            @SerializedName("avatar")
            var avatar: String = "",
            @SerializedName("city")
            var city: String = "",
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("district")
            var district: String = "",
            @SerializedName("email")
            var email: String = "",
            @SerializedName("email_verified_at")
            var emailVerifiedAt: Any = Any(),
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("is_verified")
            var isVerified: Int = 0,
            @SerializedName("name")
            var name: String = "",
            @SerializedName("phone")
            var phone: Any = Any(),
            @SerializedName("role")
            var role: String = "",
            @SerializedName("subscription_plans")
            var subscriptionPlans: Int = 0,
            @SerializedName("support_chat")
            var supportChat: Int = 0,
            @SerializedName("updated_at")
            var updatedAt: String = "",
            @SerializedName("usertype")
            var usertype: String = "",
            @SerializedName("verification_code")
            var verificationCode: String = ""
        )
    }
}