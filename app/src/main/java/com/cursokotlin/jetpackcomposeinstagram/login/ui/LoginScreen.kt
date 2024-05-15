package com.cursokotlin.jetpackcomposeinstagram.login.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontStyle
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
import kotlinx.coroutines.Delay

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        /*Ordenamos los elementos de la aplicación separados en módulos anteriormente
        Para asegurarnos de cumplir con la distribución de la aplicación se han usado
        diferentes métodos de ordenación como Box y Column, además de sus atributos
         */

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

//La función footer solo cuenta con el divider que separa el texto inferior del resto de la app

@Composable
fun Footer(modifier: Modifier) {

    Divider()
}

//SignUp emula la pantalla de iniciar sesión con una cuenta ya existente

@Composable
fun SignUp() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Don't have an account? ")
        Text(
            text = "Sign up", //En este caso utilizamos los atributos de Text para emular la pantalla original
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4EA8E9)
        )
    }
}

//Body contiene el mayor número de funciones de la app, ya que se encarga de su ordenación
//Además, nos da acceso a los parámetros definidos en la ViewModel

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

        ImageLogo( //icono de la parte superior de la aplicación
            modifier = Modifier
                .padding(25.dp)
        )

        /*Disposición de los principales elementos de la app: cajas de texto y funciones login*/

        Email(
            email = email,
            onTextChanged = { loginViewModel.onLoginChanged(it, password) })

        Divider(
            Modifier.height(30.dp),
            color = Color.Transparent
        )

        Password(password = password, onTextChanged = { loginViewModel.onLoginChanged(email, it) })

        Column( //columna para la ordenación de elementos, en este caso el forgotPassword
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End //alineamos a la derecha
        ) {
            ForgotPassword(modifier = Modifier)
        }


        LoginButton(loginEnable = isLoginEnable, loginViewModel)

        LoginDivider()

        SocialLogin()

    }
}

//SocialLogin es la función encargada de mostrar las opciones de inicio de sesión adicionales

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

//LoginDivider es la función encargada de separar las dos partes de la app

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

//LoginButton se encarga de las funcionalidades del botón de inicio de sesión

@Composable
fun LoginButton(
    loginEnable: Boolean,
    loginViewModel: LoginViewModel
) { //incluimos la ViewModel en el constructor para poder usar la función enableLogin

    val context =
        LocalContext.current //context de la aplicación para invocar un Toast (debe estar en un función Composable)

    Button(
        modifier = Modifier
            .fillMaxWidth() //hacemos que el botón se extienda a toda la pantalla
            .padding(25.dp) //ajustamos la medida
            .height(50.dp),
        colors = ButtonDefaults.buttonColors( //cambiamos sus colores por los corporativos
            backgroundColor = if (loginEnable) Color(0xFF4EA8E9) else (Color.LightGray) //si el usuario no cumple las condiciones el botón no será funcional y lo mostramos de forma gráfica
        ),

        onClick = {

            //recogemos los valores de la ViewModel para operar en el butón
            val email = loginViewModel.email.value
            val password = loginViewModel.password.value

            if (email != null && password != null) { //no podemos trabajar con los datos sin comprobar que no sean nulos


                if (loginViewModel.enableLogin(email, password)) { //utilizamos la función para comprobar que se cumplen los patrones de email y password

                    //Si se han cumplido lanzamos un Toast informando de ello

                    Toast.makeText(context, "Login correct", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Welcome $email", Toast.LENGTH_SHORT).show()


                } else {

                    //En caso contrario avisamos del error del usuario

                    Toast.makeText(context, "Email or password incorrect", Toast.LENGTH_SHORT)
                        .show()

                }
            }

            else {

                //En caso de nulos avisamos de que escriba el contenido necesario para el login

                Toast.makeText(context, "Please, specify your email and password", Toast.LENGTH_SHORT)
                    .show()
            }

        }) {

        Text(
            text = "Log In"
        )

    }

}

//Función que muestra la línea de recuperar la contraseña

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

//Función que permite al usuario la entrada de texto para la contraseña

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = password,
        onValueChange = onTextChanged,
        placeholder = { //Añadimos un placeholder al TextField para indicar al usuario que debe introducir
            Text(text = "Password")
        },
        maxLines = 1, //Limitamos el número de líneas a introducir
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), //Limitamos los carácteres que permite usar el teclado
        visualTransformation = //Añadimos funcionalidad para que pueda ocultarse la contraseña
        if (passwordVisibility) VisualTransformation.None //Si PasswordVisibility se activa no transformamos los carácteres
        else PasswordVisualTransformation(), //Si se activa los ocultamos

        trailingIcon = { //Añadimos el icono para que el usuario controle la funcionalidad
            IconButton(
                onClick = {

                    passwordVisibility = !passwordVisibility //Al pulsar en él cambiamos el estado de passwordVisibility
                }) {

                Icon(
                    imageVector =
                    if (passwordVisibility) Icons.Default.Visibility //Seleccionamos que icono mostrar en cada estado y su descripción
                    else Icons.Default.VisibilityOff,
                    contentDescription =
                    if (passwordVisibility) "Hide Password"
                    else "Show Password"
                )
            }
        }
    )
}

//Función que permite al usuario la entrada de texto para el email

@Composable
fun Email(email: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        value = email,
        onValueChange = onTextChanged,
        placeholder = { //Añadimos un placeholder al TextField para indicar al usuario que debe introducir
            Text(text = "Phone number, username or email")
        },
        maxLines = 1, //Limitamos el número de líneas a introducir
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email) //Limitamos los carácteres que permite usar el teclado
    )


}

//Función que añade el logo de la app a la pantalla de login

@Composable
fun ImageLogo(modifier: Modifier) {

    Image(
        painterResource(
            id = R.drawable.instagramlogo
        ),
        contentDescription = "App logo",
        modifier = Modifier
            .padding(25.dp)
            .height(120.dp)
    )

}

//Función que añade un icono para cerrar la aplicación en el caso de que el usuario lo pulse

@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}

