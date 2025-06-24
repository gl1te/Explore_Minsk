package com.dev.exploreminsk.presentation.edit_profile

import android.net.Uri

sealed class EditProfileEvent {
    data class OnChangeUsername(val username: String): EditProfileEvent()
    data class OnChangeEmail(val email: String): EditProfileEvent()
    data class OnChangeImage(val image: ByteArray, val uri: Uri): EditProfileEvent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OnChangeImage

            if (!image.contentEquals(other.image)) return false

            return true
        }

        override fun hashCode(): Int {
            return image.contentHashCode()
        }
    }

    object OnDismissDialog: EditProfileEvent()
    object  OnSubmit: EditProfileEvent()
}