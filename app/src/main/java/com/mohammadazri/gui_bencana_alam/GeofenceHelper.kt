package com.mohammadazri.gui_bencana_alam

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.util.Constant
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_DELAY
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_DOUBLE_GREAT
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_DOUBLE_MAJOR
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_DOUBLE_MINOR
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_DOUBLE_MODERATE
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_FLOAT
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_REQUEST_CODE

class GeofenceHelper(val context: Context) {
    private var pendingIntent: PendingIntent? = null

    fun getGeofencingRequest(listDisaster: List<Disaster>): GeofencingRequest {
        val listGeofence = ArrayList<Geofence>()
        var geofenceRadiusFloat = GEOFENCE_RADIUS_FLOAT
        listDisaster.map { disaster ->
            disaster.latLng?.let { latLng ->
                disaster.mag?.let { mag ->
                    geofenceRadiusFloat = when {
                        mag.toDouble() in 2.0..3.9 -> GEOFENCE_RADIUS_DOUBLE_MINOR
                        mag.toDouble() in 4.0..5.9 -> GEOFENCE_RADIUS_DOUBLE_MODERATE
                        mag.toDouble() in 6.0..7.9 -> GEOFENCE_RADIUS_DOUBLE_MAJOR
                        mag.toDouble() >= 8 -> GEOFENCE_RADIUS_DOUBLE_GREAT
                        else -> GEOFENCE_RADIUS_FLOAT
                    }
                } ?: run {
                    geofenceRadiusFloat = GEOFENCE_RADIUS_FLOAT
                }
                Log.d("GeofenceHelper", "$geofenceRadiusFloat")
                listGeofence.add(
                    Geofence.Builder()
                        .setRequestId(disaster.id)
                        .setCircularRegion(
                            latLng.latitude,
                            latLng.longitude,
                            geofenceRadiusFloat
                        )
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                        .setLoiteringDelay(GEOFENCE_DELAY)
                        .setNotificationResponsiveness(1000)
                        .build()
                )
            }
        }

        Log.d("GeofenceHelper", "${listGeofence})")

        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(listGeofence)
            .build()
    }

    fun getPendingIntent(): PendingIntent {
        pendingIntent?.let {
            Log.d("GeofenceHelper", "Engga Kosong")
            return it
        } ?: run {
            Log.d("GeofenceHelper", "Kosong")
            val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
            pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    GEOFENCE_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            return pendingIntent as PendingIntent
        }
    }
}