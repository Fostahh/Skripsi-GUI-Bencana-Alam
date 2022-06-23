package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
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
class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: UseCase

    @Before
    fun setup() {
        viewModel = SharedViewModel(useCase)
    }

    @Test
    fun `getDisasters success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisasters()

        `when`(useCase.getDisasters()).then {
            Resource.Success(dummyDisasters)
        }
        viewModel.listDisasterLiveData.value = dummyDisasters
        assertNotNull(viewModel.listDisasterLiveData.value?.size)
        assertEquals(viewModel.listDisasterLiveData.value, useCase.getDisasters().data)
    }

    @Test
    fun `getDisasters error and no data`() = runTest {
        `when`(useCase.getDisasters()).then {
            Resource.Error("Terjadi kesalahan", null)
        }
        viewModel.toastErrorLiveData.value = "Terjadi kesalahan"
        assertNull(viewModel.listDisasterLiveData.value)
        assertEquals(viewModel.toastErrorLiveData.value, useCase.getDisasters().message)
    }

    @Test
    fun `getDisastersByFilter gempa success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisastersByFilterGempa()

        `when`(useCase.getDisastersByFilter("gempa")).then {
            Resource.Success(dummyDisasters)
        }

        viewModel.filteredLiveData.value = dummyDisasters
        assertNotNull(viewModel.filteredLiveData.value?.size)
        assertEquals(viewModel.filteredLiveData.value, useCase.getDisastersByFilter("gempa").data)
    }

    @Test
    fun `getDisastersByFilter banjir success and data exist`() = runTest {
        val dummyDisasters = DataDummy.generateDummyDisasters()

        `when`(useCase.getDisastersByFilter("banjir")).then {
            Resource.Success(dummyDisasters)
        }
        viewModel.filteredLiveData.value = dummyDisasters
        assertNotNull(viewModel.filteredLiveData.value?.size)
        assertEquals(viewModel.filteredLiveData.value, useCase.getDisastersByFilter("banjir").data)
    }

    @Test
    fun `getDisastersByFilter error and no data`() = runTest {
        `when`(useCase.getDisastersByFilter("banjir")).then {
            Resource.Error("Terjadi kesalahan", null)
        }
        viewModel.toastErrorLiveData.value = "Terjadi kesalahan"
        assertNull(viewModel.filteredLiveData.value)
        assertEquals(
            viewModel.toastErrorLiveData.value,
            useCase.getDisastersByFilter("banjir").message
        )
    }

    @Test
    fun `getDisasterById success and data exist`() = runTest {
        val dummyDisaster = DataDummy.generateDummyDisaster()
        `when`(useCase.getDisasterById(dummyDisaster.id)).then {
            Resource.Success(dummyDisaster)
        }

        viewModel.disasterLiveData.value = dummyDisaster
        assertNotNull(viewModel.disasterLiveData.value)
        assertEquals(
            viewModel.disasterLiveData.value,
            useCase.getDisasterById(dummyDisaster.id).data
        )
    }

    @Test
    fun `getDisasterById error and no data`() = runTest {
        val dummyDisaster = DataDummy.generateDummyDisaster()
        `when`(useCase.getDisasterById(dummyDisaster.id)).then {
            Resource.Error("Terjadi kesalahan", null)
        }

        viewModel.toastErrorLiveData.value = "Terjadi kesalahan"
        assertNull(viewModel.disasterLiveData.value)
        assertEquals(
            viewModel.toastErrorLiveData.value,
            useCase.getDisasterById(dummyDisaster.id).message
        )
    }

}