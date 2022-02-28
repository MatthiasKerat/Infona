package kerasinoapps.kapps.infona.common

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: ()-> Flow<ResultType>,//Query from database
    crossinline fetch: suspend () ->RequestType, //Fetch from Api
    crossinline saveFetchResult: suspend (RequestType)->Unit,//save new api data into database
    crossinline onFetchFailed: (Throwable)-> Unit = { Unit },// Posibility to pass a function that accesses a potential error
    crossinline shouldFetch: (ResultType) -> Boolean = {true}
) = flow {
    emit(Resource.Loading(null)) //emits loading outside the flow corutine
    val data = query().first() //first emission will be list of notes and then saved into data val

    val flow = if(shouldFetch(data)){ //If this is true -> Fetching data from the api
        emit(Resource.Loading(data)) // Hier können die Daten von der Datenbank schonmal gezeigt werden während die von der Api noch geladen werden
        try {
            val fetchedResult = fetch() //Fetch function returns result of API-Call
            saveFetchResult(fetchedResult) //Save fetched result into db
            query().map {
                Resource.Success(it) // List of notes from the database after they got inserted from the api result
            }
        }catch(t : Throwable){
            onFetchFailed(t)
            query().map {
                Resource.Error("Couldnt reach server. It might be down",it)
            }
        }
    }else{
        query().map {
            Resource.Success(it)
        }
    }

    emitAll(flow)

}