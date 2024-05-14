package com.cursokotlin.jetpackcomposeinstagram.login.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.cursokotlin.jetpackcomposeinstagram.Greeting
import com.cursokotlin.jetpackcomposeinstagram.R
import com.cursokotlin.jetpackcomposeinstagram.ui.theme.JetpackComposeInstagramTheme

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Header(
            modifier = Modifier
                .padding(
                    10.dp
                )
        )
        Body(
            modifier = Modifier
                .padding(top = 150.dp),
            loginViewModel = loginViewModel
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Footer(
                modifier = Modifier

            )

            SignUp()

        }

    }
}

@Composable
fun Footer(modifier: Modifier) {

Divider()


}

@Composable
fun SignUp() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.Center
    ){
        Text(text = "Don't have an account? ")
        Text(text = "Sign up")
    }
}

@Composable
fun Body(modifier: Modifier, loginViewModel: LoginViewModel) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val isLoginEnable: Boolean by loginViewModel.isLoginEnable.observeAsState(initial = false)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ImageLogo(
            modifier = Modifier
                .padding(25.dp)
        )

        Email(email = email, onTextChanged = { loginViewModel.onLoginChanged(it, password) })



        Divider(
            Modifier.height(30.dp),
            color = Color.White
        )

        Password(password = password, onTextChanged = { loginViewModel.onLoginChanged(email, it) })


        Column( //columna para la ordenación de elementos, en este caso el forgotPassword
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End //alineamos a la derecha
        ) {
            ForgotPassword(modifier = Modifier)
        }



        LoginButton(loginEnable = isLoginEnable)

        LoginDivider()

        SocialLogin()

    }
}

@Composable
fun SocialLogin() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 35.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fb),
            contentDescription = "Social login fb",
            modifier = Modifier
                .size(16.dp)
        )
        Text(
            text = "Continue as Aris",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color(0xFF4EA8E9)
        )
    }
}

@Composable
fun LoginDivider() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB5B5B5)
        )
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
    }
}

@Composable
fun LoginButton(loginEnable: Boolean) {

    Button(
        modifier = Modifier
            .fillMaxWidth() //hacemos que el botón se extienda a toda la pantalla
            .padding(25.dp) //ajustamos la medida
            .height(50.dp),
        colors = ButtonDefaults.buttonColors( //cambiamos sus colores por los corporativos
            backgroundColor = Color(0xFF4EA8E9)
        ),

        onClick = {



        }) {

        Text(
            text = "Log In"
        )

    }

}


@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot password?",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EA8E9),
        modifier = modifier
            .padding(top = 20.dp)
    )
}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = "Password")
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),

        trailingIcon = {
            IconButton(
                onClick = {

                    passwordVisibility = !passwordVisibility
                }) {

                Icon(
                    imageVector =
                    if (passwordVisibility) Icons.Default.Visibility
                    else Icons.Default.VisibilityOff,
                    contentDescription =
                    if (passwordVisibility) "Hide Password"
                    else "Show Password"
                )
            }
        }
    )
}

@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        value = email,
        onValueChange = onTextChanged,
        placeholder = {
            Text(text = "Phone number, username or email")
        }
    )


}

@Composable
fun ImageLogo(modifier: Modifier) {

    Image(
        painterResource(id = R.drawable.insta),
        contentDescription = "App logo",
        modifier = Modifier
            .padding(25.dp)
    )

}

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}

