package com.tasleem.fribe

import android.util.Log

sealed class FribeActions {
    class SearchPlaces(val text: String) : FribeActions()
    class SearchPlaceDetails(val text: String, val location: String?) : FribeActions()
    class NearbySearch(val location: String, val radius: String? = null, val locationName: String? = null) : FribeActions()
    class Distance(val originLatLng: String, val destinationLatLng: String, val annotations: List<String> = emptyList()) : FribeActions()
    class DistanceWithPolyline(val originLatLng: String, val destinationLatLng: String, val steps: Boolean = true, val overview: GeometryOverview = GeometryOverview.Simplified) : FribeActions()
    class DistanceWithGeoJSON(val originLatLng: String, val destinationLatLng: String, val steps: Boolean = true, val overview: GeometryOverview = GeometryOverview.Simplified) : FribeActions()


    val url: String
        get() = when (this) {
            is SearchPlaces -> "https://maps.fribe.io/api/user/place/textsearch"
            is SearchPlaceDetails -> "https://maps.fribe.io/api/user/place/findplacefromtext"
            is Distance -> "https://maps.fribe.io/api/user/place/distance"
            is DistanceWithPolyline -> "https://maps.fribe.io/api/user/place/directions"
            is DistanceWithGeoJSON -> "https://maps.fribe.io/api/user/place/directions"
            is NearbySearch -> "https://maps.fribe.io/api/user/place/nearbysearch"
            else -> {
                Log.e("FribeActions", "Unsupported action type: ${this::class.java.simpleName}")
                throw IllegalArgumentException("Unsupported action type: ${this::class.java.simpleName}")
            }
        }

    val parameters: Map<String, String>
        get() {
            return when (this) {
                is SearchPlaces -> mapOf("search" to text, "publishableKey" to (FribeSDKConfiguration.publishableKey ?: ""))
                is SearchPlaceDetails -> {
                    val params = mutableMapOf("search" to text, "publishableKey" to (FribeSDKConfiguration.publishableKey ?: ""))
                    location?.let { params["location"] = it }
                    params
                }
                is Distance -> {
                    val params = mutableMapOf(
                        "origin_latlng" to originLatLng,
                        "destination_latlg" to destinationLatLng,
                        "publishableKey" to (FribeSDKConfiguration.publishableKey ?: "")
                    )
                    if (annotations.isNotEmpty()) {
                        params["annotations"] = annotations.joinToString(",")
                    }

                    params
                }
                is DistanceWithPolyline -> mapOf(
                    "origin_latlng" to originLatLng,
                    "destination_latlg" to destinationLatLng,
                    "steps" to if (steps) "true" else "false",
                    "publishableKey" to (FribeSDKConfiguration.publishableKey ?: ""),
                    "geometries" to GeometryType.Polyline.name.lowercase(),
                    "overview" to overview.name.lowercase()
                )
                is DistanceWithGeoJSON -> mapOf(
                    "origin_latlng" to originLatLng,
                    "destination_latlg" to destinationLatLng,
                    "steps" to if (steps) "true" else "false",
                    "publishableKey" to (FribeSDKConfiguration.publishableKey ?: ""),
                    "geometries" to GeometryType.Geojson.name.lowercase(),
                    "overview" to overview.name.lowercase()
                )
                is NearbySearch -> {
                    val params = mutableMapOf(
                        "location" to location,
                        "publishableKey" to (FribeSDKConfiguration.publishableKey ?: "")
                    )
                    radius?.let { params["radius"] = it }
                    locationName?.let { params["name"] = it }
                    params
                }
                else -> {
                    Log.e("FribeActions", "Unsupported action type: ${this::class.java.simpleName}")
                    throw IllegalArgumentException("Unsupported action type: ${this::class.java.simpleName}")
                }
            }
        }
}

enum class GeometryType {
    Polyline, Geojson
}

enum class GeometryOverview {
    Simplified
}