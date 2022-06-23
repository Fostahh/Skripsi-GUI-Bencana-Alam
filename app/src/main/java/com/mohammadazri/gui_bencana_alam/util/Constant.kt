package com.mohammadazri.gui_bencana_alam.util

object Constant {
    const val GEOFENCE_RADIUS_FLOAT = 100f
    const val GEOFENCE_RADIUS_DOUBLE_MINOR = 130026.0f
    const val GEOFENCE_RADIUS_DOUBLE_MODERATE = 197006.0f
    const val GEOFENCE_RADIUS_DOUBLE_MAJOR = 263860.0f
    const val GEOFENCE_RADIUS_DOUBLE_GREAT = 263860.0f

    const val GEOFENCE_REQUEST_CODE = 0
    const val GEOFENCE_DELAY = 5000

    //Radius based on Magnitude Level
    const val CIRCLE_RADIUS_DOUBLE = 100.0
    const val CIRCLE_RADIUS_DOUBLE_MINOR = 130026.0
    const val CIRCLE_RADIUS_DOUBLE_MODERATE = 197006.0
    const val CIRCLE_RADIUS_DOUBLE_MAJOR = 263860.0
    const val CIRCLE_RADIUS_DOUBLE_GREAT = 263860.0

    const val FUSED_LOCATION_INTERVAL = 90000L
    const val FUSED_LOCATION_FASTEST_INTERVAL = 60000L

    const val TURN_ON_GPS_REQUEST_CODE = 100
    const val PERMISSION_LOCATION_REQUEST_CODE = 1

    const val NOTIFICATION_ID = 33
    const val CHANNEL_ID = "GeofenceChannel"
}