package com.mohammadazri.gui_bencana_alam.util

import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.response.DisasterItemDTO
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

object DataDummy {
    fun generateDummyDisastersDTO(): List<DisasterItemDTO?> {
        val listDisasterDTO = ArrayList<DisasterItemDTO>()

        listDisasterDTO.add(
            DisasterItemDTO(
                id = "1539591567769407488",
                filter = "banjir",
                username = "Joetambs25",
                text = "bapak - ibu mohon bantuan nya jalan stm atas banjir lumayan tinggi bnyk pengendara motor yang mogok https://t.co/wg4mzmdxw9",
                predicted = "0",
                createdAt = "Wed Jun 22 12:50:08",
                mag = null,
                location = "Hinona Coffee & Roastery",
                lon = "3.535427",
                lat = "98.691519"
            )
        )

        return listDisasterDTO
    }

    fun generateDummyDisastersDTOGempa(): List<DisasterItemDTO?> {
        val listDisasterDTO = ArrayList<DisasterItemDTO>()

        listDisasterDTO.add(
            DisasterItemDTO(
                id = "1539572451645870081",
                filter = "gempa",
                username = "dewirusmila07",
                text = "rt @infobmkg: #gempa mag:4.9, 22-jun-22 13:48:08 wib, lok:2.10 lu, 96.46 bt (pusat gempa berada di laut 42 km tenggara sinabang), kedlmn:10",
                createdAt = "Wed Jun 22 11:34:11",
                predicted = "0",
                mag = "4.9",
                location = null,
                lon = "96.46",
                lat = "2.1"
            )
        )

        return listDisasterDTO
    }

    fun generateDummyDisasterDTO(): DisasterItemDTO =
        DisasterItemDTO(
            id = "1539572451645870081",
            filter = "gempa",
            username = "dewirusmila07",
            text = "rt @infobmkg: #gempa mag:4.9, 22-jun-22 13:48:08 wib, lok:2.10 lu, 96.46 bt (pusat gempa berada di laut 42 km tenggara sinabang), kedlmn:10",
            createdAt = "Wed Jun 22 11:34:11",
            predicted = "0",
            mag = "4.9",
            location = null,
            lon = "96.46",
            lat = "2.1"
        )

    fun generateDummyDisasters(): List<Disaster> {
        val listDisaster = ArrayList<Disaster>()

        listDisaster.add(
            Disaster(
                id = "1539591567769407488",
                filter = "banjir",
                username = "Joetambs25",
                text = "bapak - ibu mohon bantuan nya jalan stm atas banjir lumayan tinggi bnyk pengendara motor yang mogok https://t.co/wg4mzmdxw9",
                predicted = "0",
                createdAt = "Wed Jun 22 12:50:08",
                mag = null,
                location = "Hinona Coffee & Roastery",
                latLng = LatLng(
                    98.691519,
                    3.535427
                )
            )
        )

        return listDisaster
    }

    fun generateDummyDisastersByFilterGempa(): List<Disaster> {
        val listDisaster = ArrayList<Disaster>()

        listDisaster.add(
            Disaster(
                id = "1539572451645870081",
                filter = "gempa",
                username = "dewirusmila07",
                text = "rt @infobmkg: #gempa mag:4.9, 22-jun-22 13:48:08 wib, lok:2.10 lu, 96.46 bt (pusat gempa berada di laut 42 km tenggara sinabang), kedlmn:10",
                createdAt = "Wed Jun 22 11:34:11",
                predicted = "0",
                mag = "4.9",
                location = null,
                latLng = LatLng(
                    2.1,
                    96.46
                )
            )
        )

        return listDisaster
    }

    fun generateDummyDisaster(): Disaster =
        Disaster(
            id = "1539572451645870081",
            filter = "gempa",
            username = "dewirusmila07",
            text = "rt @infobmkg: #gempa mag:4.9, 22-jun-22 13:48:08 wib, lok:2.10 lu, 96.46 bt (pusat gempa berada di laut 42 km tenggara sinabang), kedlmn:10",
            createdAt = "Wed Jun 22 11:34:11",
            predicted = "0",
            mag = "4.9",
            location = null,
            latLng = LatLng(
                2.1,
                96.46
            )
        )

}