import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.ui.base.BaseViewModel
import com.example.ui.base.ViewEvent
import com.example.ui.base.ViewState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <State : ViewState, Event : ViewEvent> BasicScreen(
    viewModel: BaseViewModel<State, Event, *>,
    toolbar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onErrorDismissed: () -> Unit = {},
    onEvent: ((Event) -> Unit)? = null,
    content: @Composable (state: State) -> Unit,
    ) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    // Only trigger once per error message
    LaunchedEffect(state.error) {
        if (!state.error.isNullOrEmpty()) {
            dialogMessage = state.error
            showDialog = true
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collectLatest {
                onEvent?.invoke(it)
            }
        }
    }

    Scaffold(
        topBar = { toolbar?.invoke() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                content(state)

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                if (showDialog && dialogMessage != null) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                            dialogMessage = null
                            onErrorDismissed()
                        },
                        title = { Text("Error") },
                        text = { Text(dialogMessage!!) },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog = false
                                dialogMessage = null
                                onErrorDismissed()
                            }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    )
}
