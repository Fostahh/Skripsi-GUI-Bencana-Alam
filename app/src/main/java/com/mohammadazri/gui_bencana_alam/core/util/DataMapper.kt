package com.mohammadazri.gui_bencana_alam.core.util

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterItemDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

object DataMapper {

    fun disastersResponseToDisasterDomain(disastersDTO: List<DisasterItemDTO?>?): List<Disaster> {
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

    fun disasterResponseToDisasterDomain(disasterDTO: DisasterItemDTO): Disaster =
        Disaster(
            id = disasterDTO.id,
            filter = disasterDTO.filter,
            username = disasterDTO.username,
            text = disasterDTO.text,
            predicted = disasterDTO.predicted,
            createdAt = disasterDTO.createdAt,
            mag = disasterDTO.mag,
            location = disasterDTO.location,
            latLng = disasterDTO.lat?.let { lat ->
                disasterDTO.lon?.let { lon ->
                    LatLng(
                        lat.toDouble(),
                        lon.toDouble()
                    )
                }
            }
        )

}