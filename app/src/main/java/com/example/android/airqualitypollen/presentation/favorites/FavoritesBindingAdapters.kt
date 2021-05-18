package com.example.android.airqualitypollen.presentation.favorites

import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("saveButtonVisibleIfNotNull")
fun saveButtonVisibleIfNotNull(btn: Button, it: Any?) {
    btn.visibility = if (it != null) View.VISIBLE else View.GONE
}
