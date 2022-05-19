package com.mohammadazri.gui_bencana_alam.core.util

import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterDTO
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisastersDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

object DataMapper {

    fun DisastersResponseToDisasterDomain(disastersDTO: DisastersDTO?): List<Disaster> {
        val listDisaster = ArrayList<Disaster>()

        disastersDTO?.disasters?.let { listDisasterDTO ->
            listDisasterDTO.map {
                it?.let { disasterDTO ->

                    disasterDTO.latLng?.latitude?.let { latitude ->
                        disasterDTO.latLng.longitude?.let { longitude ->
                            Disaster(
                                disasterDTO.id,
                                LatLng(latitude, longitude),
                                disasterDTO.type
                            )
                        }
                    }
                }
            }
        }

        return listDisaster
    }

}