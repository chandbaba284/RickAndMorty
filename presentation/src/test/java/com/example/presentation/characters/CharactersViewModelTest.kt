package com.example.presentation.characters

import android.util.Log
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.example.presentation.uistate.UiState
import com.example.presentation.viewmodel.CharactersViewModel
import com.exmple.rickandmorty.GetCharactersQuery
import com.exmple.rickandmorty.fragment.Character
import com.exmple.rickandmorty.fragment.Location
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import usecases.CharacterUseCase
class CharactersViewModelTest {
    private var useCase: CharacterUseCase = mockk(relaxed = true)
    private lateinit var charactersViewModel: CharactersViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
        Dispatchers.setMain(testDispatcher)
        charactersViewModel = CharactersViewModel(useCase)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenValidCharacters_whenInvokeIsCalledInUseCase_thenEmitsMockedPagingDataToStateFlow() =
        runTest(testDispatcher) {
            val mockPagingData = getCharacters()
            coEvery { useCase.invoke() } returns flowOf(mockPagingData)
            charactersViewModel.charactersState.test {
                charactersViewModel.fetchData()
                assertThat(awaitItem()).isEqualTo(UiState.Loading)
                advanceUntilIdle()
                val successState = awaitItem()
                assertThat(successState).isInstanceOf(UiState.Success::class.java)
                val differ = AsyncPagingDataDiffer(
                    diffCallback = object : DiffUtil.ItemCallback<GetCharactersQuery.Result>() {
                        override fun areItemsTheSame(
                            oldItem: GetCharactersQuery.Result,
                            newItem: GetCharactersQuery.Result
                        ) = oldItem.character.id == newItem.character.id

                        override fun areContentsTheSame(
                            oldItem: GetCharactersQuery.Result,
                            newItem: GetCharactersQuery.Result
                        ) = oldItem == newItem
                    },
                    updateCallback = NoopListUpdateCallback(),
                    mainDispatcher = testDispatcher,
                    workerDispatcher = testDispatcher
                )
                differ.submitData((successState as UiState.Success).data)
                val actualCharacters = differ.snapshot().items
                differ.submitData(mockPagingData)
                val expectedCharacters = differ.snapshot().items
                assertThat(actualCharacters).isEqualTo(expectedCharacters)

                cancelAndConsumeRemainingEvents()

            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun getCharacters(): PagingData<GetCharactersQuery.Result> {
        val items =
            listOf(
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        location = Character.Location("", Location("", "", "")),
                        Character.Origin("",""), episode = listOf(Character.Episode("","",""))
                    ),
                ),
                GetCharactersQuery.Result(
                    "",
                    Character(
                        name = "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        Character.Location(
                            "",
                            Location("", "", ""),
                        ),
                        Character.Origin("",""), episode = listOf(Character.Episode("","",""))
                    ),

                ),
            )

        return PagingData.from(items)
    }

}
class NoopListUpdateCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
