package `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    val viewModel: LoginViewModel = hiltViewModel()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val isDataValid = validateData(email, password)
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(viewModel.loginState) {
        viewModel.loginState.collect { state ->
            if (state is LoginState.Authenticated) {
                navController.navigate(PupilMeshScreens.HOME_SCREEN.name) {
                    popUpTo(PupilMeshScreens.LOGIN_SCREEN.name) { inclusive = true }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close icon",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign In", style = MaterialTheme.typography.titleMedium)
        }

         Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 20.dp, top = 30.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                 Text("Zenithra", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Welcome Back", style = MaterialTheme.typography.displaySmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Please enter your details to sign in",
                    style = MaterialTheme.typography.bodySmall
                )

                 Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        "OR",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                 Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email Address") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                     OutlinedTextField(
                         value = password,
                         onValueChange = { password = it },
                         modifier = Modifier.fillMaxWidth(),
                         label = { Text("Password") },
                         singleLine = true,
                         visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                         keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                         trailingIcon = {
                             IconButton(
                                 onClick = { passwordVisible = !passwordVisible }
                             ) {
                                 Icon(
                                     painter = if (passwordVisible)
                                     painterResource(R.drawable.password_visibility)
                                     else
                                        painterResource(R.drawable.password_visibility_off),
                                     contentDescription = if (passwordVisible)
                                         "Hide password"
                                     else
                                         "Show password"
                                 )
                             }
                         }
                     )
                }

                 Text(
                    "Forgot password?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {   },
                     fontSize = 16.sp,
                    style = MaterialTheme.typography.bodySmall.copy(
                        textDecoration = TextDecoration.Underline
                    )

                )

                 Button(
                    onClick = {
                        scope.launch {
                            viewModel.login(email, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    enabled = isDataValid,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Sign In", style = MaterialTheme.typography.labelLarge)
                }

                 Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account?")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "Sign Up",
                        modifier = Modifier.clickable {

                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


private fun validateData(email: String, password: String): Boolean {
    return email.isNotBlank() && password.isNotBlank() &&
            email.length > 5 && email.contains("@") &&
            password.length >= 8
}