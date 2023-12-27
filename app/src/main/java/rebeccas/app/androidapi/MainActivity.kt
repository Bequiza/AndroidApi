package rebeccas.app.androidapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rebeccas.app.androidapi.ui.theme.AndroidApiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel by viewModels()
                    FetchingApi(
                        onLoadJoke = viewModel::loadJoke,
                        state = viewModel.state
                    )
                }
            }
        }
    }
}

@Composable
fun FetchingApi(
    onLoadJoke:() -> Unit,
    state: JokeResult
) {
    LaunchedEffect(true) {
        onLoadJoke()
    }

    Column {
        if (state == JokeResult.Loading) {
            CircularProgressIndicator()
        } else if (state is JokeResult.Success) {
            Text(
                text = state.joke,
                modifier = Modifier.padding(16.dp)
            )
        }
        LoadJokeButton(onClick = onLoadJoke)
    }
}

@Composable
fun LoadJokeButton(onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Text(text = "Get a new joke")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidApiTheme {
        FetchingApi(
            onLoadJoke = {},
            state = JokeResult.Loading
        )
    }
}