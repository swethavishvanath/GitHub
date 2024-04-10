package com.example.github
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.github.ui.theme.GitHubTheme
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    // Declare the launcher at the top of your Activity/Fragment:

    private val googleAuthUiClient by lazy {
        Log.d("MyApplication", " calling my naveen...")
        FirebaseApp.clearInstancesForTest()
        val options = FirebaseOptions.Builder()
            .setApplicationId("1:957876149890:android:c36e6dd28480fecb2f5e9b")
            .setApiKey("AIzaSyC2H6lQgYYo8daxeJlOjMp6UBcHX-Y_vLo")
            .setProjectId("github-8e1ba")
            .build()
        FirebaseApp.initializeApp(this, options)
        val firebaseApp = FirebaseApp.getInstance()
        if (firebaseApp != null) {
            Log.d("MyApplication", "Firebase initialized successfully")
        } else {
            Log.e("MyApplication", "Firebase initialization failed")
        }
        Log.d("MyApplication", " called my friend...")

        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Create FirebaseOptions with the desired configuration
        // Create FirebaseOptions with the desired configuration
        // Create FirebaseOptions with the desired configuration

        super.onCreate(savedInstanceState)
        val githubService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
        val repositoryDao = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "repository-db"
        ).build().repositoryDao()
        //val repositoryDao = MyClass.database.repositoryDao()



        Log.d("MyApplication", "Initializing Firebase...")
        Log.d("MyApplication", "Firebase initialization completed.");
        setContent {
            GitHubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = viewModel<SignInViewModel>()

                    // Inside your activity or fragment
                    val repositoryRepository = RepositoryRepository(githubService,repositoryDao) // Initialize this properly
                    val viewModelFactory = RepositoryViewModelFactory(repositoryRepository)
                    val rePViewModel = ViewModelProvider(this, viewModelFactory).get(RepositoryViewModel::class.java)

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {

                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("repositorylist")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("repositorylist")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }

                        composable("MyApp()") {
                            val newMessageReceived = AppState.newMessageReceived
                            Column {
                                if (newMessageReceived) {
                                    Text("SignedIn Successfully", color = Color.Red)
                                } else {
                                    Text("You are not able to use the app")
                                }
                            }
                        }

                        composable("repositorylist") {
                            RepositoryListScreen(viewModel = rePViewModel, navController = navController)
                        }

                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
