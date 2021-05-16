package com.example.android.airqualitypollen.presentation.details

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.airqualitypollen.business.airquality.entity.AirQuality
import java.text.DecimalFormat

@BindingAdapter("currentLocation")
fun bindCurrentLocation(locationTV: TextView, airQuality: AirQuality?) {
    locationTV.text = if (airQuality != null) "${airQuality.lat}, ${airQuality.lng}" else ""
}

@BindingAdapter("currentDescription")
fun bindCurrentDescription(descriptionTV: TextView, airQuality: AirQuality?) {
    val data = ArrayList<String>()
    if (airQuality == null) {
        descriptionTV.text = ""
        return
    }
    if (airQuality.placeName != null) {
        data.add(airQuality.placeName)
    }
    if (airQuality.postalCode != null) {
        data.add(airQuality.postalCode)
    }
    if (airQuality.division != null) {
        data.add(airQuality.division)
    }
    if (airQuality.state != null) {
        data.add(airQuality.state)
    }
    if (airQuality.countryCode != null) {
        data.add(airQuality.countryCode)
    }
    descriptionTV.text = data.joinToString(separator = ", ")
}

@BindingAdapter("spinnerGoneIfNotNull")
fun spinnerGoneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("formatDecimal")
fun formatDecimal(valueTV: TextView, value: Double?) {
    val df = DecimalFormat("##0.00")
    valueTV.text = if (value != null) df.format(value) else ""
}

@BindingAdapter("formatInteger")
fun formatInteger(valueTV: TextView, value: Int?) {
    valueTV.text = value?.toString() ?: ""
}
