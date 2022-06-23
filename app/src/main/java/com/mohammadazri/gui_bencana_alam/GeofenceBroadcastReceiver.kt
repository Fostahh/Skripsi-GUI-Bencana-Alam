package com.mohammadazri.gui_bencana_alam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.mohammadazri.gui_bencana_alam.util.NotificationUtil

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        val notificationUtil = NotificationUtil(context)

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            notificationUtil.sendGeofenceEnteredNotification("Anda memasuki daerah bencana alam")
        }
    }
}