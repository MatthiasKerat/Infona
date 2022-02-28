package kerasinoapps.kapps.infona.main_module.presentation.viewmodel.rangliste

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.use_case.GetUserImageUseCase
import kerasinoapps.kapps.infona.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RanglisteViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getUserImageUseCase: GetUserImageUseCase
) : ViewModel(){

    private val _users = mutableStateOf<List<User>>(listOf())
    val users: State<List<User>> = _users

    private val _state = mutableStateOf(RanglisteState())
    val state:State<RanglisteState> = _state



    fun getUsers(){

        getUsersUseCase().onEach { result ->

            when(result){
                is Resource.Loading -> {
                    _state.value = _state.value.copy(showData = false, error = null, loading = true)
                }

                is Resource.Success -> {
                    _users.value = result.data!!.sortedByDescending { it.coins }
                    _state.value = _state.value.copy(showData = true, error = null, loading = false)
                    getPictures()
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = result.message ?: "An unknown error occured", showData = false, loading = false)
                }
            }

        }.launchIn(viewModelScope)

    }

    fun getPictures(){
        var userList:MutableList<User> = mutableListOf()
        viewModelScope.launch {
            for(user in _users.value){
                val img = getUserImageUseCase(user.profilPictureUrl).collect { result ->
                    when(result){
                        is Resource.Success -> {
                            user.userImage = result.data!!
                            userList.add(user)
                        }
                        is Resource.Error -> {
                            userList.add(user)
                        }
                    }
                }
            }
            _users.value = userList

        }

    }
}