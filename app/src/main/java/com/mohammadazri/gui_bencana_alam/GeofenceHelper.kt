package com.mohammadazri.gui_bencana_alam

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_DELAY
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_RADIUS_FLOAT
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_REQUEST_CODE
import com.mohammadazri.gui_bencana_alam.util.Constant.GEOFENCE_REQUEST_ID
import com.mohammadazri.gui_bencana_alam.util.ext.toMapLatLng

class GeofenceHelper(val context: Context) {
    private var pendingIntent: PendingIntent? = null

    fun getGeofencingRequest(listDisaster: List<Disaster>): GeofencingRequest {
        val listGeofence = ArrayList<Geofence>()

        listDisaster.map { disaster ->
            disaster.id?.let { id ->
                disaster.latLng?.let { latLng ->
                    listGeofence.add(
                        Geofence.Builder()
                            .setRequestId(id.toString())
                            .setCircularRegion(
                                latLng.latitude,
                                latLng.longitude,
                                GEOFENCE_RADIUS_FLOAT
                            )
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                            .setLoiteringDelay(GEOFENCE_DELAY)
                            .setNotificationResponsiveness(1000)
                            .build()
                    )
                }
            }
        }

        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(listGeofence)
            .build()
    }

    fun getPendingIntent(): PendingIntent {
        pendingIntent?.let {
            return it
        } ?: run {
            val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
            pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    GEOFENCE_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            return pendingIntent as PendingIntent
        }
    }
}