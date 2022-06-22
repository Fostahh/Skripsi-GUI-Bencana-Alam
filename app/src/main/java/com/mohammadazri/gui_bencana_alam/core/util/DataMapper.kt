package com.mohammadazri.gui_bencana_alam.core.util

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersItem
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

object DataMapper {

    fun disastersResponseToDisasterDomain(disastersDTO: List<DisastersItem?>?): List<Disaster> {
        val listDisaster = ArrayList<Disaster>()

        disastersDTO?.let {
            it.map { disaster ->
                disaster?.let {
                    disaster.lat?.let { lat ->
                        disaster.lon?.let { lon ->
                            listDisaster.add(
                                Disaster(
                                    id = disaster.id,
                                    filter = disaster.filter,
                                    username = disaster.username,
                                    text = disaster.text,
                                    predicted = disaster.predicted,
                                    createdAt = disaster.createdAt,
                                    mag = disaster.mag,
                                    location = disaster.location,
                                    latLng = LatLng(
                                        lat.toDouble(),
                                        lon.toDouble()
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }

        return listDisaster
    }
}