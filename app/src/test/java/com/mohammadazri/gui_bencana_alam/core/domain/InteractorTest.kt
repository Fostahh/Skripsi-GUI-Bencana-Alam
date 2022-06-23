package com.mohammadazri.gui_bencana_alam.core.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.Interactor
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import com.mohammadazri.gui_bencana_alam.util.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class InteractorTest {

    private lateinit var interactor: Interactor

    @Mock
    private lateinit var repository: IRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        interactor = Interactor(repository)
    }

    @Test
    fun `getDisasters success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisasters()

        `when`(repository.getDisasters()).thenReturn(Resource.Success(dummyDisasters))

        assertNotNull(interactor.getDisasters().data)
        assertEquals(interactor.getDisasters().data, repository.getDisasters().data)
    }

    @Test
    fun `getDisasters error and no data`() = runTest {
        `when`(repository.getDisasters()).thenReturn(Resource.Error("Terjadi kesalahan", null))

        assertNull(interactor.getDisasters().data)
        assertEquals(interactor.getDisasters().message, repository.getDisasters().message)
    }


    @Test
    fun `getDisastersByFilter gempa success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisastersByFilterGempa()

        `when`(repository.getDisastersByFilter("gempa")).then {
            Resource.Success(dummyDisasters)
        }

        assertNotNull(interactor.getDisastersByFilter("gempa").data)
        assertEquals(
            interactor.getDisastersByFilter("gempa").data,
            repository.getDisastersByFilter("gempa").data
        )
    }

    @Test
    fun `getDisastersByFilter banjir success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisasters()

        `when`(repository.getDisastersByFilter("banjir")).then {
            Resource.Success(dummyDisasters)
        }

        assertNotNull(interactor.getDisastersByFilter("banjir").data)
        assertEquals(
            interactor.getDisastersByFilter("banjir").data,
            repository.getDisastersByFilter("banjir").data
        )
    }

    @Test
    fun `getDisasterByFilter error and no data`() = runTest {
        `when`(repository.getDisastersByFilter("banjir")).then {
            Resource.Error("Terjadi kesalahan", null)
        }

        assertNull(repository.getDisastersByFilter("banjir").data)
        assertEquals(
            interactor.getDisastersByFilter("banjir").message,
            repository.getDisastersByFilter("banjir").message
        )
    }

    @Test
    fun `getDisasterById success and data exist`() = runTest {
        val dummyDisaster = DataDummy.generateDummyDisaster()

        `when`(repository.getDisasterById(dummyDisaster.id)).then {
            Resource.Success(dummyDisaster)
        }

        assertNotNull(interactor.getDisasterById(dummyDisaster.id).data)
        assertEquals(
            interactor.getDisasterById(dummyDisaster.id).data,
            repository.getDisasterById(dummyDisaster.id).data
        )
    }

    @Test
    fun `getDisasterById error and no data`() = runTest {
        val dummyDisaster = DataDummy.generateDummyDisaster()

        `when`(repository.getDisasterById(dummyDisaster.id)).then {
            Resource.Error("Terjadi kesalahan", null)
        }

        assertNull(interactor.getDisasterById(dummyDisaster.id).data)
        assertEquals(
            interactor.getDisasterById(dummyDisaster.id).data,
            repository.getDisasterById(dummyDisaster.id).data
        )
    }
}