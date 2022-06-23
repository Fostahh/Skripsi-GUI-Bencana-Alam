package com.mohammadazri.gui_bencana_alam.core.data

import com.google.android.gms.location.FusedLocationProviderClient
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.IRemoteDataSource
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.util.ApiResponse
import com.mohammadazri.gui_bencana_alam.core.util.DataMapper
import com.mohammadazri.gui_bencana_alam.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private lateinit var repository: Repository

    @Mock
    private lateinit var remoteDataSource: IRemoteDataSource

    @Mock
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Before
    fun setup() {
        repository = Repository(fusedLocationProviderClient, remoteDataSource)
    }

    @Test
    fun `getDisasters success and data exist`() = runTest {
        val dummyDisastersDTO = DataDummy.generateDummyDisastersDTO()

        `when`(remoteDataSource.getDisasters()).then {
            ApiResponse.Success(dummyDisastersDTO)
        }

        val actualResult = DataMapper.disastersResponseToDisasterDomain(dummyDisastersDTO)
        assertNotNull(repository.getDisasters().data)
        assertEquals(
            repository.getDisasters().data,
            actualResult
        )
    }

    @Test
    fun `getDisasters succes and data not exists`() = runTest {
        `when`(remoteDataSource.getDisasters()).then {
            ApiResponse.Error("Bencana Alam tidak ditemukan")
        }

        assertNull(repository.getDisasters().data)
        assertEquals(repository.getDisasters().message, "Bencana Alam tidak ditemukan")
    }

    @Test
    fun `getDisasters error and no data`() = runTest {
        `when`(remoteDataSource.getDisasters()).then {
            ApiResponse.Error("Terjadi kesalahan")
        }

        assertNull(repository.getDisasters().data)
        assertEquals(repository.getDisasters().message, "Terjadi kesalahan")
    }

    @Test
    fun `getDisastersByFilter gempa success and data exist`() = runTest {
        val dummyDisastersDTO = DataDummy.generateDummyDisastersDTOGempa()

        `when`(remoteDataSource.getDisastersByFilter("gempa")).then {
            ApiResponse.Success(dummyDisastersDTO)
        }

        val actualResult = DataMapper.disastersResponseToDisasterDomain(dummyDisastersDTO)
        assertNotNull(repository.getDisastersByFilter("gempa").data)
        assertEquals(
            repository.getDisastersByFilter("gempa").data,
            actualResult
        )
    }

    @Test
    fun `getDisastersByFilter gempa success and data not exist`() = runTest {
        `when`(remoteDataSource.getDisastersByFilter("gempa")).then {
            ApiResponse.Error("Bencana Alam tidak ditemukan")
        }

        assertNull(repository.getDisastersByFilter("gempa").data)
        assertEquals(
            repository.getDisastersByFilter("gempa").message,
            "Bencana Alam tidak ditemukan"
        )
    }

    @Test
    fun `getDisastersByFilter banjir success and data exist`() = runTest {
        val dummyDisastersDTO = DataDummy.generateDummyDisastersDTO()

        `when`(remoteDataSource.getDisastersByFilter("banjir")).then {
            ApiResponse.Success(dummyDisastersDTO)
        }

        val actualResult = DataMapper.disastersResponseToDisasterDomain(dummyDisastersDTO)
        assertNotNull(repository.getDisastersByFilter("banjir").data)
        assertEquals(
            repository.getDisastersByFilter("banjir").data,
            actualResult
        )
    }

    @Test
    fun `getDisastersByFilter banjir success and data not exist`() = runTest {
        `when`(remoteDataSource.getDisastersByFilter("banjir")).then {
            ApiResponse.Error("Bencana Alam tidak ditemukan")
        }

        assertNull(repository.getDisastersByFilter("banjir").data)
        assertEquals(
            repository.getDisastersByFilter("banjir").message,
            "Bencana Alam tidak ditemukan"
        )
    }

    @Test
    fun `getDisastersByFilter error and no data`() = runTest {
        `when`(remoteDataSource.getDisastersByFilter("banjir")).then {
            ApiResponse.Error("Terjadi kesalahan")
        }

        assertNull(repository.getDisastersByFilter("banjir").data)
        assertEquals(repository.getDisastersByFilter("banjir").message, "Terjadi kesalahan")
    }

    @Test
    fun `getDisasterById success and data exist`() = runTest {
        val dummyDisasterDTO = DataDummy.generateDummyDisasterDTO()

        `when`(remoteDataSource.getDisasterById(dummyDisasterDTO.id)).then {
            ApiResponse.Success(dummyDisasterDTO)
        }

        val actualResult = DataMapper.disasterResponseToDisasterDomain(dummyDisasterDTO)

        assertNotNull(repository.getDisasterById(dummyDisasterDTO.id).data)
        assertEquals(repository.getDisasterById(dummyDisasterDTO.id).data, actualResult)
    }

    @Test
    fun `getDisasterById success and data not exist`() = runTest {

        `when`(remoteDataSource.getDisasterById("1")).then {
            ApiResponse.Error("Bencana Alam tidak ditemukan")
        }

        assertNull(repository.getDisasterById("1").data)
        assertEquals(repository.getDisasterById("1").message, "Bencana Alam tidak ditemukan")
    }

    @Test
    fun `getDisasterById error and no data`() = runTest {
        val dummyDisasterDTO = DataDummy.generateDummyDisasterDTO()

        `when`(remoteDataSource.getDisasterById(dummyDisasterDTO.id)).thenReturn(ApiResponse.Error("Terjadi kesalahan"))

        assertNull(repository.getDisasterById(dummyDisasterDTO.id).data)
        assertEquals(repository.getDisasterById(dummyDisasterDTO.id).message, "Terjadi kesalahan")
    }
}