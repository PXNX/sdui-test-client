package nyx.sdui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nyx.sdui.Status
import nyx.sdui.network.Repository

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"
    val result = MutableStateFlow<Status<Any>>(Status.Loading())

    init {
        fetchContent()
    }

    private fun fetchContent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                result.value = Status.Loading()
                delay(3000L)
                result.value = Status.Success(Repository.getContent())
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                result.value = Status.Failure(e)
            }
        }
    }

    fun performClick(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                result.value = Status.Loading()
                delay(3000L)
                result.value = Status.Success(Repository.performClick(id))
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                result.value = Status.Failure(e)
            }
        }
    }
}