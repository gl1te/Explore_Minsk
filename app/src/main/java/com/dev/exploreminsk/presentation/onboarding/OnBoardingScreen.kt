package com.dev.exploreminsk.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun OnBoardingScreen(
    onComplete: () -> Unit,
    onEvent: (OnBoardingEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.onboarding_background),
            contentDescription = "onboarding_background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            Spacer(modifier = Modifier.height(190.dp))
            Text(
                text = stringResource(R.string.plan_your_label),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = montserratFontFamily,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                textAlign = TextAlign.Left
            )
            Text(
                text = stringResource(R.string.luxurious_vacation_label),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFontFamily,
                    lineHeight = 42.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(135.dp))
            Button(
                onClick = {
                    onComplete()
                    onEvent(OnBoardingEvent.SaveAppEntry)
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.custom_blue)
                )
            ) {
                Text(
                    text = stringResource(R.string.explore_label),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}