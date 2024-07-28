package com.tasleem.fribe


import com.google.gson.annotations.SerializedName

// PlaceDetailsResponse
data class PlaceDetailsResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("data") val data: Place?
)

// PlaceSearchResponse
data class PlaceSearchResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("_pagination") val pagination: Pagination,
    @SerializedName("data") val data: List<Place>
)

// NearbySearchResponse
data class NearbySearchResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Place>
) : APIResponse

// Pagination
data class Pagination(
    @SerializedName("total") val total: Int,
    @SerializedName("totalPage") val totalPage: Int,
    @SerializedName("currentPage") val currentPage: Int
)

// Place
data class Place(
    @SerializedName("_id") val id: String?,
    @SerializedName("shortId") val shortId: String?,
    @SerializedName("formattedAddress") val formattedAddress: String,
    @SerializedName("name") val name: String,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String,
    @SerializedName("alpha2") val alpha2: String?,
    @SerializedName("alpha3") val alpha3: String?,
    @SerializedName("countryCode") val countryCode: String?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?
) {
    val idValue: String get() = id ?: ""
    val shortIdValue: String get() = shortId ?: ""
    val formattedAddressValue: String get() = formattedAddress
    val nameValue: String get() = name
    val cityValue: String get() = city
    val countryValue: String get() = country
    val alpha2Value: String get() = alpha2 ?: ""
    val alpha3Value: String get() = alpha3 ?: ""
    val countryCodeValue: String get() = countryCode ?: ""
    val latitudeValue: Double get() = latitude ?: 0.0
    val longitudeValue: Double get() = longitude ?: 0.0
}

// ErrorResponse
data class ErrorResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("_metadata") val metadata: Metadata
)

// Metadata
data class Metadata(
    @SerializedName("languages") val languages: List<String>,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("version") val version: String
)

// DistanceWithGeometryResponse
data class DistanceWithGeometryResponse(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("message") val message: String?,
//    @SerializedName("data") val data: DistanceWithPolylineData?
    @SerializedName("routes") val routes: List<Route>?,
    @SerializedName("waypoints") val waypoints: List<Waypoint>?,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("duration") val duration: Double?
) : APIResponse

// DistanceWithPolylineData
data class DistanceWithPolylineData(
    @SerializedName("routes") val routes: List<Route>?,
    @SerializedName("waypoints") val waypoints: List<Waypoint>?,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("duration") val duration: Double?
)

// Route
data class Route(
    @SerializedName("geometry") val geometry: String?,
    @SerializedName("legs") val legs: List<Leg>,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("weight_name") val weightName: String?,
    @SerializedName("weight") val weight: Double?
)

// Leg
data class Leg(
    @SerializedName("steps") val steps: List<Step>,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("weight") val weight: Double?
)

// Step
data class Step(
    @SerializedName("intersections") val intersections: List<Intersection>,
    @SerializedName("driving_side") val drivingSide: String?,
    @SerializedName("geometry") val geometry: String?,
    @SerializedName("mode") val mode: String?,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("maneuver") val maneuver: Maneuver,
    @SerializedName("ref") val ref: String?,
    @SerializedName("weight") val weight: Double?,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("name") val name: String?
)

// Intersection
data class Intersection(
    @SerializedName("out") val out: Int?,
    @SerializedName("entry") val entry: List<Boolean>,
    @SerializedName("bearings") val bearings: List<Int>,
    @SerializedName("location") val location: List<Double>
)

// Maneuver
data class Maneuver(
    @SerializedName("bearing_after") val bearingAfter: Int,
    @SerializedName("location") val location: List<Double>,
    @SerializedName("bearing_before") val bearingBefore: Int,
    @SerializedName("type") val type: String
)

// Waypoint
data class Waypoint(
    @SerializedName("hint") val hint: String?,
    @SerializedName("distance") val distance: Double?,
    @SerializedName("name") val name: String?,
    @SerializedName("location") val location: List<Double>
)

// DistanceModel
data class DistanceModel(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("message") val message: String?,
//    var data: T?

    @SerializedName("durations") val durations: List<List<Double>>,
    @SerializedName("destinations") val destinations: List<Location>,
    @SerializedName("sources") val sources: List<Location>,
    @SerializedName("distances") val distances: List<List<Double>>?
)

// DistanceData
data class DistanceData(
    @SerializedName("durations") val durations: List<List<Double>>,
    @SerializedName("destinations") val destinations: List<Location>,
    @SerializedName("sources") val sources: List<Location>,
    @SerializedName("distances") val distances: List<List<Double>>?
)

// Location
data class Location(
    @SerializedName("hint") val hint: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: List<Double>
)

// GeoJSONResponse
data class GeoJSONResponse(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("message") val message: String?,
//    @SerializedName("data") val data: GeoJSONData

    @SerializedName("routes") val routes: List<GeoJSONRoute>,
    @SerializedName("waypoints") val waypoints: List<GeoJSONWaypoint>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
) : APIResponse

// GeoJSONData
data class GeoJSONData(
    @SerializedName("routes") val routes: List<GeoJSONRoute>,
    @SerializedName("waypoints") val waypoints: List<GeoJSONWaypoint>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double
)

// GeoJSONRoute
data class GeoJSONRoute(
    @SerializedName("geometry") val geometry: GeoJSONGeometry,
    @SerializedName("legs") val legs: List<GeoJSONLeg>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double,
    @SerializedName("weight_name") val weightName: String,
    @SerializedName("weight") val weight: Double
)

// GeoJSONGeometry
data class GeoJSONGeometry(
    @SerializedName("coordinates") val coordinates: List<List<Double>>,
    @SerializedName("type") val type: String
)

// GeoJSONLeg
data class GeoJSONLeg(
    @SerializedName("steps") val steps: List<GeoJSONStep>,
    @SerializedName("distance") val distance: Double,
    @SerializedName("duration") val duration: Double,
    @SerializedName("summary") val summary: String,
    @SerializedName("weight") val weight: Double
)

// GeoJSONStep
data class GeoJSONStep(
    @SerializedName("intersections") val intersections: List<GeoJSONIntersection>,
    @SerializedName("driving_side") val drivingSide: String,
    @SerializedName("geometry") val geometry: GeoJSONGeometry,
    @SerializedName("mode") val mode: String,
    @SerializedName("duration") val duration: Double,
    @SerializedName("maneuver") val maneuver: GeoJSONManeuver,
    @SerializedName("ref") val ref: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("distance") val distance: Double,
    @SerializedName("name") val name: String
)

// GeoJSONIntersection
data class GeoJSONIntersection(
    @SerializedName("out") val out: Int?,
    @SerializedName("entry") val entry: List<Boolean>,
    @SerializedName("bearings") val bearings: List<Int>,
    @SerializedName("location") val location: List<Double>,
    @SerializedName("in") val `in`: Int?
)

// GeoJSONManeuver
data class GeoJSONManeuver(
    @SerializedName("bearing_after") val bearingAfter: Int,
    @SerializedName("location") val location: List<Double>,
    @SerializedName("bearing_before") val bearingBefore: Int,
    @SerializedName("type") val type: String
)

// GeoJSONWaypoint
data class GeoJSONWaypoint(
    @SerializedName("hint") val hint: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("name") val name: String,
    @SerializedName("location") val location: List<Double>
)
