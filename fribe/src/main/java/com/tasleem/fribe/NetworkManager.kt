package com.tasleem.fribe


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

object NetworkManager {

    private const val BASE_URL = "https://maps.fribe.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)

    interface ApiService {
        @GET("api/user/place/textsearch")
        suspend fun searchPlaces(@QueryMap params: Map<String, String>): PlaceSearchResponse

        @GET("api/user/place/findplacefromtext")
        suspend fun searchPlaceDetails(@QueryMap params: Map<String, String>): PlaceDetailsResponse

        @GET("api/user/place/distance")
        suspend fun getDistance(@QueryMap params: Map<String, String>): DistanceModel
//        suspend fun getDistance(@QueryMap params: Map<String, String>): DistanceModel

        @GET("api/user/place/directions")
        suspend fun getDistanceWithPolyline(@QueryMap params: Map<String, String>): DistanceWithGeometryResponse

        @GET("api/user/place/nearbysearch")
        suspend fun nearbySearch(@QueryMap params: Map<String, String>): NearbySearchResponse

        @GET("api/user/place/directions")
        suspend fun getDistanceWithGeoJSON(@QueryMap params: Map<String, String>): GeoJSONResponse
    }
}