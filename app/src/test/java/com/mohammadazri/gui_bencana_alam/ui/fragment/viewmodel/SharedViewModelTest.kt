package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mohammadazri.gui_bencana_alam.core.domain.model.Disaster
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.Interactor
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import com.mohammadazri.gui_bencana_alam.util.DataDummy
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: UseCase

    private val listDisasterLiveData = MutableLiveData<List<Disaster>>()

    @Before
    fun setup() {
        viewModel = SharedViewModel(useCase)
    }

    @Test
    suspend fun `getDisasters return success with data`() {
        val dummyDisasters = DataDummy.generateDummyDisasters()

        `when`(useCase.getDisasters()).then {
            Resource.Success(dummyDisasters)
        }
        val disastersUseCase = useCase.getDisasters().data

//        viewModel.listDisasterLiveData.value = dummyDisasters
        assertNotNull(viewModel.listDisasterLiveData.value?.size)
        assertEquals(viewModel.listDisasterLiveData.value, disastersUseCase)
    }
}