package com.dev.exploreminsk.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onEvent: (LoginEvent) -> Unit,
    navigateToSignUp: () -> Unit,
    onLoginSuccess: ()->Unit
) {
    val context = LocalContext.current

    if (state.loginSuccess) {
        LaunchedEffect(Unit) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.onboarding_background),
            contentDescription = "onboarding_background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if(state.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .zIndex(2f)
            )

            LinearProgressIndicator(
                color = colorResource(R.color.custom_blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .zIndex(3f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.90f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(R.string.minsk_label),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = colorResource(R.color.custom_blue),
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = montserratFontFamily,
                        letterSpacing = 4.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = stringResource(R.string.email_label),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = colorResource(R.color.custom_blue),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp),
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = state.email,
                    onValueChange = {
                        onEvent(LoginEvent.OnEmailChange(it))
                    },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = montserratFontFamily,
                        fontSize = 16.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.email_template_label),
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = montserratFontFamily,
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .border(
                            width = 2.dp,
                            color = if (state.emailError != null) colorResource(R.color.custom_red)
                            else colorResource(
                                R.color.custom_blue
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                state.emailError?.let {
                    Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                        Text(
                            text = stringResource(it),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = colorResource(R.color.custom_red),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Left
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.password_label),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = colorResource(R.color.custom_blue),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = montserratFontFamily,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp),
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = state.password,
                    onValueChange = {
                        onEvent(LoginEvent.OnPasswordChange(it))
                    },
                    textStyle = MaterialTheme.typography.labelLarge.copy(
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = montserratFontFamily,
                        fontSize = 16.sp
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.password_template_label),
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = montserratFontFamily,
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .border(
                            width = 2.dp,
                            color = if (state.passwordError != null) colorResource(R.color.custom_red)
                            else colorResource(
                                R.color.custom_blue
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    maxLines = 1,
                    singleLine = true
                )
                state.passwordError?.let {
                    Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                        Text(
                            text = stringResource(it),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = colorResource(R.color.custom_red),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Left
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier =Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(end = 35.dp)
                            .clickable {
                                Toast.makeText(context, "8800...", Toast.LENGTH_SHORT).show()
                            },
                        textAlign = TextAlign.Right
                    )
                }

                state.loginError?.let {
                    Row(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)) {
                        Text(
                            text = stringResource(it),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = colorResource(R.color.custom_red),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 30.dp),
                            textAlign = TextAlign.Left
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Button(
                    onClick = {
                        onEvent(LoginEvent.OnSubmit)
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.custom_blue)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.login_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(105.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dont_have_an_account_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Gray,
                            fontSize = 13.sp,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = stringResource(R.string.sign_up_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = colorResource(R.color.custom_blue),
                            fontSize = 13.sp,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .clickable {
                                navigateToSignUp()
                            }
                    )
                }
            }
        }
    }
}