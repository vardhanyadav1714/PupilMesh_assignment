package `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import `in`.innovaticshub.pupilmesh_assignment.presentation.PupilMeshScreens
import `in`.innovaticshub.pupilmesh_assignment.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val viewmodel: LoginViewModel = hiltViewModel()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(viewmodel.loginState) {
        viewmodel.loginState.collect { state ->
            when (state) {
                is LoginState.Authenticated -> {
                    navController.navigate(PupilMeshScreens.HOME_SCREEN.name) {
                        popUpTo(PupilMeshScreens.LOGIN_SCREEN.name) { inclusive = true }
                    }
                }
                else -> {

                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
         Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close icon")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign In", textAlign = TextAlign.Center)
        }

         Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp, start = 7.dp, end = 7.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.70f),
                contentAlignment = Alignment.Center
            ) {
                Card(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp, bottom = 20.dp)
                    ) {
                         Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Zenithra", fontSize = 22.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Welcome Back", fontSize = 35.sp)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("Please enter your details to sign in", fontSize = 12.sp)
                        }

                         Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.google),
                                contentDescription = "Google login",
                                modifier = Modifier.size(80.dp)
                            )
                            Image(
                                painter = painterResource(R.drawable.apple),
                                contentDescription = "Apple login",
                                modifier = Modifier.size(80.dp)
                            )
                        }

                         Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth(0.45f)
                                    .padding(start = 20.dp),
                                color = Color.Gray
                            )
                            Text("OR")
                            HorizontalDivider(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth(0.45f)
                                    .padding(end = 20.dp),
                                color = Color.Gray
                            )
                        }

                         Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Your Email Address") },
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Password") },
                                maxLines = 1
                            )
                        }

                         Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp, top = 20.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                "Forgot password?",
                                textDecoration = TextDecoration.Underline
                            )
                        }

                         Column(
                            modifier = Modifier
                                 .fillMaxWidth()
                                .padding(top = 20.dp),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            val isDataValid = validateData(email, password)
                            Button(
                                onClick = {
                                    if (isDataValid) {
                                        scope.launch {
                                            if (validateData(email, password)) {
                                                viewmodel.login(email, password)
                                                navController.navigate(PupilMeshScreens.HOME_SCREEN.name){
                                                    popUpTo(PupilMeshScreens.LOGIN_SCREEN.name){inclusive=true}
                                                }
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .align(Alignment.CenterHorizontally),
                                enabled = isDataValid,
                                colors = if (isDataValid) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                                else ButtonDefaults.buttonColors(Color.LightGray)
                            ) {
                                Text("Sign In", fontSize = 16.sp)
                            }

                             Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("Don't have an account?")
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Sign Up", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun validateData(email: String, password: String): Boolean {
    return email.isNotBlank() && password.isNotBlank() && email.length > 10 && email.contains("@") && password.length > 10
}