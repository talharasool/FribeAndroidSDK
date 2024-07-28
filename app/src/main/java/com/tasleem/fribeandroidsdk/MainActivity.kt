package com.tasleem.fribeandroidsdk

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tasleem.fribe.DistanceDelegate
import com.tasleem.fribe.DistanceModel
import com.tasleem.fribe.DistanceWithGEOJSONDelegate
import com.tasleem.fribe.DistanceWithGeometryResponse
import com.tasleem.fribe.DistanceWithPolylineDelegate
import com.tasleem.fribe.FribeActions
import com.tasleem.fribe.FribeSDKConfiguration
import com.tasleem.fribe.FribeService
import com.tasleem.fribe.FribeServiceErrorDelegate
import com.tasleem.fribe.GeoJSONData
import com.tasleem.fribe.NearbySearchDelegate
import com.tasleem.fribe.Pagination
import com.tasleem.fribe.Place
import com.tasleem.fribe.PlaceDetailsDelegate
import com.tasleem.fribe.PlaceSearchDelegate
import com.tasleem.fribeandroidsdk.ui.theme.FribeAndroidSDKTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity(), PlaceSearchDelegate,
    PlaceDetailsDelegate,
    FribeServiceErrorDelegate,
    NearbySearchDelegate,
    DistanceDelegate,
    DistanceWithGEOJSONDelegate,
    DistanceWithPolylineDelegate {

    private lateinit var fribeService: FribeService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("MainActivity: onCreate called")

        val originLat = "17.6125018,54.0344293"
        val destLat = "21.473534,55.975414"

        // Step 1: Call the SDK Configuration setup
        FribeSDKConfiguration.setup(
            clientId = "",
            secretKey = "",
            publishableKey = ""
        )

        println("MainActivity: FribeSDKConfiguration setup completed")

        // Step 2: Create the FribeService instance
        fribeService = FribeService(
            placeSearchDelegate = this,
            placeDetailsDelegate = this,
            distanceDelegate = this,
            nearbySearchDelegate = this,
            distanceWithGEOJSONDelegate = this,
            distanceWithPolylineDelegate = this,
            errorDelegate = this
        )



        enableEdgeToEdge()
        setContent {
            FribeAndroidSDKTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Fribe",
                        modifier = Modifier.padding(innerPadding),
                        onSearchPlacesClick = { makeSearchPlacesCall() },
                        onSearchDetailsClick = { makeSearchDetailsCall() },
                        onNearbySearchClick = { makeNearbySearchCall() },
                        onDistanceSearchClick = { makeDistanceSearchCall() },
                        onDistanceGEOJSONClick = {makeGEOJSONCall()},
                        onDistancePolylineClick = {makePolyLlineCall()}

                    )
                }
            }
        }
    }

    private fun makeSearchPlacesCall() {
        val searchAction = FribeActions.SearchPlaces("oman")
        makeApiCall(searchAction)
    }

    private fun makeSearchDetailsCall() {
        val searchDetailAction = FribeActions.SearchPlaceDetails("oman", "17.0177578%2C54.0805267")
        makeApiCall(searchDetailAction)
    }

    private fun makeNearbySearchCall() {
        val nearBySearch = FribeActions.NearbySearch(
            location = "17.6125018,54.0344293",
            radius = "5000000",
            locationName = "صلالة"
        )
        makeApiCall(nearBySearch)
    }

    private fun makeDistanceSearchCall() {
        val distanceSearch = FribeActions.Distance(
            originLatLng = "57.535793,22.376252",
            destinationLatLng = "57.535236,22.376083",
            annotations = listOf("distance", "duration")
        )
        makeApiCall(distanceSearch)
    }


    private fun makeGEOJSONCall() {
        val distanceGEOJSON = FribeActions.DistanceWithGeoJSON(originLatLng = "57.535793,22.376252",
            destinationLatLng = "57.535236,22.376083",
        )
        makeApiCall(distanceGEOJSON)
    }

    private fun makePolyLlineCall() {
        val distanceGEOJSON = FribeActions.DistanceWithPolyline(originLatLng = "57.535793,22.376252",
            destinationLatLng = "57.535236,22.376083",
            steps = true
        )
        makeApiCall(distanceGEOJSON)
    }

    private fun makeApiCall(action: FribeActions) {
        println("MainActivity: Before making API call")
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Make the API call in the IO context
                withContext(Dispatchers.IO) {
                    println("MainActivity: Making API call")
                    fribeService.request(action)
                }
            } catch (e: Exception) {
                println("MainActivity: Making API call Exceptions")
                e.printStackTrace()
            }
        }
    }

    override fun didReceivePlaceSearch(places: List<Place>, pagination: Pagination) {
        // Handle the success case
        println("MainActivity: didReceivePlaceSearch called")
        places.forEach {
            println("Place: ${it.name}, Address: ${it.formattedAddress}")
        }
        runOnUiThread {
            Toast.makeText(this, "Success: Place Search Called ${places.size} places", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailReceivePlaceSearch(message: String) {
        // Handle the failure case
        println("MainActivity: didFailReceivePlaceSearch called")
        println("Failed to search places: $message")
        runOnUiThread {
            Toast.makeText(this, "Failed to search places: $message", Toast.LENGTH_LONG).show()
        }
    }

    override fun didReceivePlaceDetailsResponse(place: Place) {
        // Handle the failure case
        println("MainActivity: didReceivePlaceDetailsResponse called")
        runOnUiThread {
            Toast.makeText(this, "Success: Place Detail Called: ${place.name}", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailReceivePlaceDetailsResponse(message: String) {

        println("Failed to search detail places: $message")
        runOnUiThread {
            Toast.makeText(this, "Failed to search detail places: $message", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailWithError(error: Throwable) {
        Log.e("DID FAIL WITH ERR::",error.toString())
        runOnUiThread {
            Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun didReceiveGEOJSON(distanceGEOJSON: GeoJSONData) {
        println("MainActivity: didReceiveGEOJSON called")
        println("Success to search detail places: ${distanceGEOJSON.routes.size}")
        runOnUiThread {
            Toast.makeText(this, "GEO JSON${distanceGEOJSON.routes.size}", Toast.LENGTH_LONG).show()
        }
    }



    override fun didReceiveDistanceWithGeometryResponse(distancePolyline: DistanceWithGeometryResponse) {
        runOnUiThread {
            Toast.makeText(this, "Success: Found Polyline: ${distancePolyline.routes?.first()?.geometry}", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailReceiveDistanceWithGeometryResponse(message: String) {
        println("MainActivity: didFailReceiveDistanceWithGeometryResponse called")
        println("Failed to Get GeometryResponse : $message")
        runOnUiThread {
            Toast.makeText(this, "Failed to Geometry API Call: $message", Toast.LENGTH_LONG).show()
        }
    }

    override fun didReceiveNearbySearch(places: List<Place>) {
        println("Success to didReceiveNearbySearch: ${places.size}")
        runOnUiThread {
            Toast.makeText(this, "Success: Found didReceiveNearbySearch: ${places.size}", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailReceiveNearbySearch(message: String) {
        println("Failed to Get Near By : $message")
        runOnUiThread {
            Toast.makeText(this, "Failed to Near By Call: $message", Toast.LENGTH_LONG).show()
        }
    }

    override fun didReceiveDistanceResponse(distance: DistanceModel) {
        println("Success to didReceiveDistanceResponse: ${distance.distances?.size}")
        runOnUiThread {

            Toast.makeText(this, "Response Received: Distance ${distance.distances?.size}" +
                    "\nSources ${distance.sources.size} " +
                    "\nDuration${distance.durations.size} }", Toast.LENGTH_LONG).show()
        }
    }

    override fun didFailReceiveDistanceResponse(message: String) {
        println("Failed to Distance : $message")
        runOnUiThread {
            Toast.makeText(this, "Failed to Get Distance : $message", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onSearchPlacesClick: () -> Unit,
    onSearchDetailsClick: () -> Unit,
    onNearbySearchClick: () -> Unit,
    onDistanceSearchClick: () -> Unit,
    onDistanceGEOJSONClick: () -> Unit,
    onDistancePolylineClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hello $name!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSearchPlacesClick) {
            Text(text = "Search Places")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onSearchDetailsClick) {
            Text(text = "Search Details")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onNearbySearchClick) {
            Text(text = "Nearby Search")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDistanceSearchClick) {
            Text(text = "Distance Search")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDistanceSearchClick) {
            Text(text = "Distance Search")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDistanceGEOJSONClick) {
            Text(text = "Distance GEOJSON")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onDistancePolylineClick ) {
            Text(text = "Distance Polyline")
        }
    }
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    FribeAndroidSDKTheme {
        Greeting(
            name = "FRIBE",
            onSearchPlacesClick = {},
            onSearchDetailsClick = {},
            onNearbySearchClick = {},
            onDistanceSearchClick = {},
            onDistanceGEOJSONClick = {},
            onDistancePolylineClick = {}
        )
    }
}