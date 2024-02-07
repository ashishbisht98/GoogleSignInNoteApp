package com.notesAppGoogleSignIn.googleSignIn.userInterface

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.notesAppGoogleSignIn.Greeting
import com.notesAppGoogleSignIn.R
import com.notesAppGoogleSignIn.googleSignIn.model.User
import com.notesAppGoogleSignIn.noteApp.userInterface.MainActivity
import com.notesAppGoogleSignIn.googleSignIn.viewmodel.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import com.notesAppGoogleSignIn.utils.AuthResultContract


@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun AuthScreen(
    authViewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    val user by remember(authViewModel) { authViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val context = LocalContext.current

    val viewModel = remember {
        mutableStateOf("")
    }

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google sign in failed"
                } else {
                    coroutineScope.launch {
                        account.email?.let {
                            account.displayName?.let { it1 ->
                                authViewModel.signIn(
                                    email = it,
                                    displayName = it1,
                                )
                            }
                        }
                    }
                }
            } catch (e: ApiException) {
                text = "Google sign in failed"
            }
        }

    AuthView(
        errorText = text,
        onClick = {
            text = null
            authResultLauncher.launch(signInRequestCode)
        }
    )

    user?.let {
//          HomeScreen(user = it)
//        Greeting(user = it)
        //        MainActivity(user=it)
        //ForwardUserDetails(user=it)
        // context.startActivity(Intent(context,MainActivity::class.java))
        val data = User(it.email, it.displayName)
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("userDetails", data)
        }
        context.startActivity(intent)
    }
}


@Composable
fun AuthView(
    errorText: String?,
    onClick: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInButton(
                text = "Sign in with Google",
                loadingText = "Signing in...",
                isLoading = isLoading,
                icon = painterResource(id = R.drawable.ic_google_logo),
                onClick = {
                    isLoading = true
                    onClick()
                }
            )

            errorText?.let {
                isLoading = false
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = it)
            }
        }
    }
}

