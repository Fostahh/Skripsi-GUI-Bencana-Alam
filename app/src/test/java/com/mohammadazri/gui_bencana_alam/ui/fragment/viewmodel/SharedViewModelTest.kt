package com.mohammadazri.gui_bencana_alam.ui.fragment.viewmodel

import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.Interactor
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import com.mohammadazri.gui_bencana_alam.core.util.Resource
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class SharedViewModelTest : TestCase() {

    private lateinit var viewModel: SharedViewModel

    @Mock
    private lateinit var useCase: UseCase

    @Before
    fun setup() {
        viewModel = SharedViewModel(useCase)
    }

    @Test
    fun `getDisasters return success with data`() {
        `when`(viewModel.getDisasters()).then {
            viewModel.listDisasterLiveData.postValue(DataDummy())
        }
        assertEquals(viewModel.listDisasterLiveData.value, )
    }
}