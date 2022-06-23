package com.mohammadazri.gui_bencana_alam.util

import com.google.android.gms.maps.model.LatLng
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster

object DataDummy {
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
}