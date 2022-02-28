package kerasinoapps.kapps.infona.common

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import kerasinoapps.kapps.infona.domain.model.user_info.Birthday
import java.util.*

fun birthdayToAge(birthday: Birthday):String{
    val dob = Calendar.getInstance()
    val today = Calendar.getInstance()

    dob.set(birthday.year,birthday.month,birthday.day)

    var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    if(today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
        age--
    }

    return "${age}"

}

fun getPathFromURI(context: Context, uri: Uri):String?{
    val path: String? = uri.path
    var realPath: String? = null

    val databaseUri: Uri
    val selection: String?
    val selectionArgs: Array<String>?
    if (path?.contains("/document/image:") == true) { // files selected from "Documents"
        databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        selection = "_id=?"
        selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
    } else { // files selected from all other sources, especially on Samsung devices
        databaseUri = uri
        selection = null
        selectionArgs = null
    }
    try {
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Media.DATE_TAKEN
        ) // some example data you can query
        val cursor = context.contentResolver.query(
            databaseUri,
            projection, selection, selectionArgs, null
        )
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(projection[0])
                realPath = cursor.getString(columnIndex)
            }
        }
        if (cursor != null) {
            cursor.close()
        }
    } catch (e: Exception) {
        //Timber.i("zeze get path error " + e.message)
    }
    return realPath
}