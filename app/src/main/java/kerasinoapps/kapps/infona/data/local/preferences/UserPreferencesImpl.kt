package kerasinoapps.kapps.infona.data.local.preferences

import android.content.SharedPreferences
import kerasinoapps.kapps.infona.common.Constants.KEY_REGISTERED
import kerasinoapps.kapps.infona.common.Constants.KEY_TOKEN
import kerasinoapps.kapps.infona.common.Constants.NO_TOKEN
import kerasinoapps.kapps.infona.common.Constants.REGISTER_UNDONE
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserPreferences {

    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN,token)
            .apply()
    }

    override fun loadToken(): String {
        return sharedPreferences.getString(KEY_TOKEN, NO_TOKEN) ?: NO_TOKEN
    }

    override fun saveRegisterStatus(status: String) {
        sharedPreferences.edit()
            .putString(KEY_REGISTERED,status)
            .apply()
    }

    override fun getRegisterStatus(): String? {
        return sharedPreferences.getString(KEY_REGISTERED, REGISTER_UNDONE)?: REGISTER_UNDONE
    }
}