package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.use_case.*
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetAllExercisesUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetAllTopicsUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetSubjectsUseCase
import kerasinoapps.kapps.infona.main_module.domain.use_case.GetTopicsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getSubjectsUseCase: GetSubjectsUseCase,
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val getAllTopicsUseCase: GetAllTopicsUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserImageUseCase: GetUserImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {

    private val _userToShow = mutableStateOf<User>(User(email="Default"))
    val userToShow: State<User> = _userToShow

    private val _ranking = mutableStateOf("")
    val ranking:State<String> = _ranking

    fun getUser(id:String){
        viewModelScope.launch {
            _userToShow.value = getUserByIdUseCase(id)
            getRanking()
            getUserImage(_userToShow.value.profilPictureUrl)
        }
    }

    fun getRanking(){
        getUsersUseCase().onEach { result ->

            when(result){
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    //_ranking.value = "${userToShow.value.coins} (${(result.data!!.sortedByDescending { it.coins }.indexOf(userToShow.value) + 1)}.)"
                    val sortedList = result.data!!.sortedByDescending { it.coins }
                    val ranking = sortedList.indexOfFirst { user ->
                        user.id == userToShow.value.id
                    }
                    _ranking.value = "${userToShow.value.coins} (${ranking+1}.)"
                }

                is Resource.Error -> {

                }
            }

        }.launchIn(viewModelScope)
    }

    /**
     * Done exercises section
     */

    private var exercises = listOf<Exercise>()
    private var topics = listOf<Topic>()
    private var subjects = listOf<Subject>()

    fun getValues(){
        var alreadyCalculated = false
        viewModelScope.launch {
            combine(getSubjectsUseCase(),getAllExercisesUseCase(), getAllTopicsUseCase()){ result1, result2, result3 ->
                when(result1){
                    is Resource.Success ->{
                        subjects = result1.data!!
                    }
                }
                when(result2){
                    is Resource.Success ->{
                        exercises = result2.data!!
                    }
                }
                when(result3){
                    is Resource.Success ->{
                        topics = result3.data!!
                    }
                }
            }.collect {

                if(exercises.isNotEmpty()&&subjects.isNotEmpty()&&topics.isNotEmpty()){
                    if (!alreadyCalculated){
                        calculateExerciseInfo()
                        alreadyCalculated = true
                    }
                }
            }
        }

    }

    private val _profileState = mutableStateOf(ProfileState())
    val profileState:State<ProfileState> = _profileState

    fun calculateExerciseInfo(){
        var list = listOf<ExerciseInfoElem>()
        for(subject in subjects){
            var counter = 0
            var doneCounter = 0
            val exerciseInfoElem = ExerciseInfoElem(subject.name,0,0)
            for (topic in topics){
                for(exercise in exercises){
                    if(topic.belongsToSubject == subject.id && exercise.belongsToTopic == topic.id){
                        counter++
                        if(userToShow.value.doneExercises.contains(exercise.id)){
                            doneCounter++
                        }
                    }
                }
            }
            exerciseInfoElem.exerciseCount = counter
            exerciseInfoElem.doneExercises = doneCounter
            list = list + exerciseInfoElem
        }
        _profileState.value = ProfileState(true,list)
    }

    /**
     * User Image
     */

    private val _imageLoading = mutableStateOf(true)
    val imageLoading:State<Boolean> = _imageLoading

    private val _imageError = mutableStateOf<String?>(null)
    val imageError:State<String?> = _imageError

    private val _image = mutableStateOf<ImageBitmap?>(null)
    val image:State<ImageBitmap?> = _image

    fun getUserImage(imagePath:String){

        getUserImageUseCase(imagePath).onEach { result ->

            when(result){
                is Resource.Loading -> {
                    _imageLoading.value = true
                }
                is Resource.Success -> {
                    _imageLoading.value = false
                    _image.value = result.data
                }

                is Resource.Error -> {
                    _imageLoading.value = false
                    _imageError.value = result.message ?: "An unknown error occured"
                }
            }

        }.launchIn(viewModelScope)

    }

    private val _uploadImageState = mutableStateOf(UploadImageState())
    val uploadImageState:State<UploadImageState> = _uploadImageState

    fun uploadImage(uri: Uri?, context:Context){

        if(uri == null){
            return
        }

        uploadImageUseCase(_userToShow.value.id, uri, context).onEach { result ->

            when(result){
                is Resource.Success -> {
                    _uploadImageState.value = _uploadImageState.value.copy(loading = false, successful = true, error = null)
                    _userToShow.value = _userToShow.value.copy(profilPictureUrl = result.data!!.profilePictureUrl)
                    updateUserUseCase(_userToShow.value)
                    setNewImage(uri, context)
                }

                is Resource.Loading -> {
                    _uploadImageState.value = _uploadImageState.value.copy(loading = true, successful = false, error = null)
                }

                is Resource.Error -> {
                    _uploadImageState.value = _uploadImageState.value.copy(loading = false, successful = false, error = "Could not upload new picture")
                }
            }

        }.launchIn(viewModelScope)

    }

    private fun setNewImage(uri:Uri, context: Context){
        _imageError.value = null
        if (Build.VERSION.SDK_INT < 28) {
            _image.value = MediaStore.Images.Media.getBitmap(context.contentResolver,uri).asImageBitmap()

        } else {
            val source = ImageDecoder.createSource(context.contentResolver,uri)
            _image.value = ImageDecoder.decodeBitmap(source).asImageBitmap()
        }
    }
}