package com.dev.exploreminsk.presentation.edit_profile

import android.net.Uri
import androidx.annotation.StringRes

data class EditProfileScreenState(
    val isLoading: Boolean = false,
    val result: Boolean = false,
    val login: String = "",
    @StringRes val loginError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    @StringRes val errorMessage: Int? = null,
    val originalEmail: String = "",
    val imageUrl: String? = null,
    val byteArray: ByteArray? = null,
    val selectedImageUri: Uri? = null,
    val showEmailConfirmationDialog: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EditProfileScreenState

        if (isLoading != other.isLoading) return false
        if (result != other.result) return false
        if (loginError != other.loginError) return false
        if (emailError != other.emailError) return false
        if (errorMessage != other.errorMessage) return false
        if (showEmailConfirmationDialog != other.showEmailConfirmationDialog) return false
        if (login != other.login) return false
        if (email != other.email) return false
        if (originalEmail != other.originalEmail) return false
        if (imageUrl != other.imageUrl) return false
        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (selectedImageUri != other.selectedImageUri) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = isLoading.hashCode()
        result1 = 31 * result1 + result.hashCode()
        result1 = 31 * result1 + (loginError ?: 0)
        result1 = 31 * result1 + (emailError ?: 0)
        result1 = 31 * result1 + (errorMessage ?: 0)
        result1 = 31 * result1 + showEmailConfirmationDialog.hashCode()
        result1 = 31 * result1 + login.hashCode()
        result1 = 31 * result1 + email.hashCode()
        result1 = 31 * result1 + originalEmail.hashCode()
        result1 = 31 * result1 + (imageUrl?.hashCode() ?: 0)
        result1 = 31 * result1 + (byteArray?.contentHashCode() ?: 0)
        result1 = 31 * result1 + (selectedImageUri?.hashCode() ?: 0)
        return result1
    }
}
