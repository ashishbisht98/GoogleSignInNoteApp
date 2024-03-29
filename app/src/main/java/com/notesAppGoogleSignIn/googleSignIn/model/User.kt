package com.notesAppGoogleSignIn.googleSignIn.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    val displayName: String
):Parcelable
