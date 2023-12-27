package rebeccas.app.androidapi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request


sealed class JokeResult {
    object Loading : JokeResult()
    data class Success(val joke: String) : JokeResult()
    data class Error(val errorMessage: String?) : JokeResult()
}

class MainViewModel : ViewModel() {

    val client = OkHttpClient()

    var state by mutableStateOf<JokeResult>(JokeResult.Loading)
        private set

    fun loadJoke() {
        viewModelScope.launch {

            state = JokeResult.Loading

            val req = Request.Builder().url("https://api.chucknorris.io/jokes/random").build()

            try {
                val response = withContext(Dispatchers.IO) {
                    client.newCall(req).execute()
                }

                val responseString = response.body!!.string()

                Log.i("APIDEBUG", responseString)

                if (response.isSuccessful && !responseString.isNullOrEmpty()) {
                    val jokeData =
                        Json { ignoreUnknownKeys = true }.decodeFromString<ApiData>(responseString)

                    Log.i("APIDEBUG2", jokeData.value)

                    state = JokeResult.Success(jokeData.value)

                } else {
                    state = JokeResult.Error("Error")
                }
            } catch (e: Exception){

            }
        }
    }
}