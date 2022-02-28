package kerasinoapps.kapps.infona.common

fun convertMonthToInt(string: String):Int{
    when(string){
        "Jan" -> return 1
        "Feb" -> return 2
        "Mrz" -> return 3
        "Apr" -> return 4
        "Mai" -> return 5
        "Jun" -> return 6
        "Jul" -> return 7
        "Aug" -> return 8
        "Sep" -> return 9
        "Okt" -> return 10
        "Nov" -> return 11
        "Dez" -> return 12
        else -> return 1
    }
}