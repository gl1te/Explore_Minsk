package com.dev.exploreminsk.presentation.edit_profile

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.dev.exploreminsk.R
import com.dev.exploreminsk.presentation.common.PulseAnimation
import com.dev.exploreminsk.presentation.ui.theme.montserratFontFamily

@Composable
fun EditProfileScreen(
    navigateToLocation: () -> Unit,
    state: State<EditProfileScreenState>,
    onEvent: (EditProfileEvent) -> Unit,
) {
    val context = LocalContext.current
    val imageUrl = state.value.imageUrl

    val imageRequest = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .placeholder(R.drawable.avatar)
            .error(R.drawable.avatar)
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = state.value.selectedImageUri?.let { uri ->
            ImageRequest.Builder(context)
                .data(uri)
                .error(R.drawable.avatar)
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build()
        } ?: imageRequest
    )

    val isImageLoaded = painter.state is AsyncImagePainter.State.Success


    val pickerMedia = rememberLauncherForActivityResult(PickVisualMedia()) {uri->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            val byteArray = uriToByteArray(context.contentResolver, uri)
            if (byteArray != null) {
                onEvent(EditProfileEvent.OnChangeImage(byteArray, uri))
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }
    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PulseAnimation(modifier = Modifier.size(120.dp))
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp)
            ) {
                if (state.value.errorMessage != null) {
                    Toast.makeText(context, state.value.errorMessage!!, Toast.LENGTH_SHORT).show()
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navigateToLocation()
                            }
                    )
                    Text(
                        text = stringResource(R.string.edit_profile_label),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = montserratFontFamily,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(25.dp))
                }
                Spacer(
                    modifier = Modifier.height(60.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "profile_avatar",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .alpha(if (isImageLoaded) 1f else 0.3f)
                            .clickable {
                                pickerMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                            },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(
                        modifier = Modifier.height(100.dp)
                    )
                    BasicTextField(
                        value = state.value.login,
                        onValueChange = {
                            onEvent(EditProfileEvent.OnChangeUsername(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 34.sp,
                            fontFamily = montserratFontFamily,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true,
                        maxLines = 1,
                        cursorBrush = SolidColor(Color.Transparent),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (state.value.login.isBlank()) {
                                    Text(
                                        text = stringResource(R.string.user_template_label),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 34.sp,
                                            fontFamily = montserratFontFamily,
                                            color = Color.Black.copy(alpha = 0.6f),
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )
                    BasicTextField(
                        value = state.value.email,
                        onValueChange = {
                            onEvent(EditProfileEvent.OnChangeEmail(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        maxLines = 1,
                        textStyle = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 26.sp,
                            fontFamily = montserratFontFamily,
                            color = Color.Gray.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        ),
                        cursorBrush = SolidColor(Color.Transparent),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (state.value.email.isBlank()) {
                                    Text(
                                        text = stringResource(R.string.email_template_label2),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 26.sp,
                                            fontFamily = montserratFontFamily,
                                            color = Color.Gray.copy(alpha = 0.7f),
                                            textAlign = TextAlign.Center
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(100.dp)
                    )
                    Button(
                        onClick = {
                            onEvent(EditProfileEvent.OnSubmit)
                        },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.custom_blue)
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.save_changes_label),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = montserratFontFamily,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (state.value.showEmailConfirmationDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            onEvent(EditProfileEvent.OnDismissDialog)
                        },
                        confirmButton = {
                            Text(
                                text = stringResource(R.string.ok_dialog_label),
                                modifier = Modifier
                                    .clickable {
                                        onEvent(EditProfileEvent.OnDismissDialog)
                                    }
                                    .padding(8.dp),
                                color = colorResource(R.color.custom_blue)
                            )
                        },
                        title = {
                            Text(
                                text = stringResource(R.string.confirm_email_dialog_label),
                                fontWeight = FontWeight.Bold,
                                fontFamily = montserratFontFamily
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.confirm_email_dialog_text_label),
                                fontFamily = montserratFontFamily,
                                fontSize = 16.sp
                            )
                        },
                        shape = RoundedCornerShape(24.dp),
                    )
                }
            }
        }
    }
}

private fun uriToByteArray(contentResolver: ContentResolver, imageUri: Uri): ByteArray? {
    val inputStream = contentResolver.openInputStream(imageUri)
    val byteArray = inputStream?.readBytes()
    inputStream?.close()
    return byteArray
}