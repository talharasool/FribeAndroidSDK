package com.tasleem.fribe




interface APIResponse

interface PlaceSearchDelegate {
    fun didReceivePlaceSearch(places: List<Place>, pagination: Pagination)
    fun didFailReceivePlaceSearch(message: String)
}

interface NearbySearchDelegate {
    fun didReceiveNearbySearch(places: List<Place>)
    fun didFailReceiveNearbySearch(message: String)
}

interface PlaceDetailsDelegate {
    fun didReceivePlaceDetailsResponse(place: Place)
    fun didFailReceivePlaceDetailsResponse(message: String)
}

interface FribeServiceErrorDelegate {
    fun didFailWithError(error: Throwable)
}

interface DistanceWithPolylineDelegate {
    fun didReceiveDistanceWithGeometryResponse(distancePolyline: DistanceWithGeometryResponse)
    fun didFailReceiveDistanceWithGeometryResponse(message: String)
}

interface DistanceWithGEOJSONDelegate {
    fun didReceiveGEOJSON(distanceGEOJSON: GeoJSONData)
    fun didFailReceiveDistanceWithGeometryResponse(message: String)
}

interface DistanceDelegate {
    fun didReceiveDistanceResponse(distance: DistanceModel)
    fun didFailReceiveDistanceResponse(message: String)
}