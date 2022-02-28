package kerasinoapps.kapps.infona.data.local.preferences

interface UserPreferences {

    fun saveToken(token: String)
    fun loadToken():String?
    fun saveRegisterStatus(status:String)
    fun getRegisterStatus():String?

}