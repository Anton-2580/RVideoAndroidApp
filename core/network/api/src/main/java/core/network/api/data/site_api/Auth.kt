package core.network.api.data.site_api


sealed class Auth(
    rawUrl: String,
): SiteAPI("auth/$rawUrl") {
    data object Login: Auth("login/")
    data object Logout: Auth("logout/")
    data object User: Auth("user/")

    sealed class Password(
        rawUrl: String,
    ): Auth("password/$rawUrl/") {
        data object Reset: Auth("reset/")
        data object RestResetConfirm: Auth("reset/confirm/")
        data object Change: Auth("change/")
        data class ResetConfirm(val uidb64: String, val token: String): Auth("reset/confirm/$uidb64/$token/")
    }
}