package com.tasleem.fribe



import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log


class FribeService(
    private val placeSearchDelegate: PlaceSearchDelegate? = null,
    private val placeDetailsDelegate: PlaceDetailsDelegate? = null,
    private val distanceDelegate: DistanceDelegate? = null,
    private val distanceWithPolylineDelegate: DistanceWithPolylineDelegate? = null,
    private val nearbySearchDelegate: NearbySearchDelegate? = null,
    private val distanceWithGEOJSONDelegate: DistanceWithGEOJSONDelegate? = null,
    private val errorDelegate: FribeServiceErrorDelegate? = null
) {

    @SuppressLint("LongLogTag")
    suspend fun request(action: FribeActions) {
        println("FribeService: request called with action ${action::class.java.simpleName}")
        try {
            when (action) {
                is FribeActions.SearchPlaces -> {
                    println("FribeService: SearchPlaces action")
                    val response = NetworkManager.api.searchPlaces(action.parameters)
                    if (response.statusCode == 200) {
                        placeSearchDelegate?.didReceivePlaceSearch(response.data, response.pagination)
                    } else {
                        placeSearchDelegate?.didFailReceivePlaceSearch(response.message)
                    }
                }
                is FribeActions.SearchPlaceDetails -> {
                    println("FribeService: SearchPlaceDetails action")
                    val response = NetworkManager.api.searchPlaceDetails(action.parameters)
                    if (response.statusCode == 200) {
                        response.data?.let {
                            placeDetailsDelegate?.didReceivePlaceDetailsResponse(it)
                        } ?: placeDetailsDelegate?.didFailReceivePlaceDetailsResponse("Sorry the details aren't available")
                    } else {
                        placeDetailsDelegate?.didFailReceivePlaceDetailsResponse("Sorry the details aren't available")
                    }
                }
                is FribeActions.Distance -> {
                    println("FribeService: Distance action")
                    val response = NetworkManager.api.getDistance(action.parameters)
                    Log.e("Distance Data is coming..", response.toString());
                    if (response.statusCode == null){
                        response.let {
                            distanceDelegate?.didReceiveDistanceResponse(it)
                        }
                    }else{
                        distanceDelegate?.didFailReceiveDistanceResponse(response.message.toString())
                    }
                }
                is FribeActions.DistanceWithPolyline -> {
                    val response = NetworkManager.api.getDistanceWithPolyline(action.parameters)
                    Log.e("Distance Polyline Data is Coming..", response.toString());
                    if (response.statusCode == null){
                        response.let {
                            distanceWithPolylineDelegate?.didReceiveDistanceWithGeometryResponse(it)
                        }
                    }else{
                        distanceDelegate?.didFailReceiveDistanceResponse(response.message.toString())
                    }
//                    if (response.statusCode == 200) {
//                        response.data?.let {
//                            distanceWithPolylineDelegate?.didReceiveDistanceWithGeometryResponse(it)
//                        } ?: distanceWithPolylineDelegate?.didFailReceiveDistanceWithGeometryResponse("Sorry, the distance with geometry data isn't available")
//                    } else {
//                        distanceWithPolylineDelegate?.didFailReceiveDistanceWithGeometryResponse(response.message)
//                    }
                }
                is FribeActions.DistanceWithGeoJSON -> {
                    println("FribeService: DistanceWithGeoJSON action")
                    val response = NetworkManager.api.getDistanceWithGeoJSON(action.parameters)
                    Log.e("GEO JSON RESPONSE...", response.routes.first().legs.size.toString())

                    if (response.statusCode == null){
                        val geoJSONData = GeoJSONData(
                            routes = response.routes,
                            waypoints = response.waypoints,
                            distance = response.distance,
                            duration = response.duration
                        )
                        response.let {
                            distanceWithGEOJSONDelegate?.didReceiveGEOJSON(geoJSONData)
                        }
                    }else{
                        distanceWithGEOJSONDelegate?.didFailReceiveDistanceWithGeometryResponse(response.message.toString())
                    }

//                    if (response.statusCode == 200) {
//                        response.let {
//                            distanceWithGEOJSONDelegate?.didReceiveGEOJSON(it)
//                        }
//                    } else {
//                        distanceWithGEOJSONDelegate?.didFailReceiveDistanceWithGeometryResponse(response.message)
//                    }
                }
                is FribeActions.NearbySearch -> {
                    println("FribeService: NearbySearch action")
                    val response = NetworkManager.api.nearbySearch(action.parameters)
                    if (response.statusCode == 200) {
                        val nearByPlaces = response.data
                        if (nearByPlaces.isEmpty()) {
                            nearbySearchDelegate?.didFailReceiveNearbySearch("Sorry, the location doesn't exist")
                        } else {
                            nearbySearchDelegate?.didReceiveNearbySearch(nearByPlaces)
                        }
                    } else {
                        placeSearchDelegate?.didFailReceivePlaceSearch(response.message)
                    }
                }
                else -> {
                    Log.e("FribeService", "Unsupported action type: ${action::class.java.simpleName}")
                }
            }
        } catch (e: Exception) {
            println("FribeService: Exception caught - ${e.message}")
            errorDelegate?.didFailWithError(e)
        }
    }
}

//MARK EXTRAS's

//                    if (response.equals(null)){
//                        val msg = response["message"].toString()
//
//                        distanceDelegate?.didFailReceiveDistanceResponse(msg)
//
//                    }else{
//                        response.let {
//                            distanceDelegate?.didReceiveDistanceResponse(it)
//                        }
//                    }


//                    if (response.statusCode == 200) {
//                        response.distances?.let {
//                            distanceDelegate?.didReceiveDistanceResponse(it)
//                        } ?: distanceDelegate?.didFailReceiveDistanceResponse("Sorry, the distance data isn't available")
//                    } else {
//                        distanceDelegate?.didFailReceiveDistanceResponse(response?.message.toString())
//                    }