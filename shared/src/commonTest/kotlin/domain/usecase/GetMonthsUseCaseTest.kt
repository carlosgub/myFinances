package domain.usecase

import app.cash.turbine.test
import com.carlosgub.myfinances.core.state.GenericState
import data.repository.impl.FakeFinanceRepositoryImpl
import kotlinx.coroutines.test.runTest
import mock.monthListFiltered
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMonthsUseCaseTest {
    private val fakeFinanceRepositoryImpl = FakeFinanceRepositoryImpl()
    private val getMonthsUseCase = GetMonthsUseCase(fakeFinanceRepositoryImpl)

    @Test
    fun `Get Months success`() =
        runTest {
            val expected = GenericState.Success(monthListFiltered)
            getMonthsUseCase().test {
                assertEquals(expected, awaitItem())
                awaitComplete()
            }
        }
}
