package com.carlosgub.myfinances.test.presentation.viewmodel.editincome

import com.carlosgub.myfinances.core.state.GenericState
import com.carlosgub.myfinances.core.utils.toMillis
import com.carlosgub.myfinances.core.utils.toMoneyFormat
import com.carlosgub.myfinances.core.utils.toStringDateFormat
import com.carlosgub.myfinances.domain.usecase.DeleteIncomeUseCase
import com.carlosgub.myfinances.domain.usecase.EditIncomeUseCase
import com.carlosgub.myfinances.domain.usecase.GetIncomeUseCase
import com.carlosgub.myfinances.presentation.viewmodel.editincome.EditIncomeScreenState
import com.carlosgub.myfinances.presentation.viewmodel.editincome.EditIncomeViewModel
import com.carlosgub.myfinances.test.data.repository.impl.FakeFinanceRepositoryImpl
import com.carlosgub.myfinances.test.mock.incomeFinanceModelOne
import kotlinx.coroutines.test.runTest
import org.orbitmvi.orbit.test.test
import kotlin.test.Test

class EditIncomeViewModelTest {
    private val fakeFinanceRepositoryImpl = FakeFinanceRepositoryImpl()
    private var editIncomeUseCase = EditIncomeUseCase(fakeFinanceRepositoryImpl)
    private var getIncomeUseCase = GetIncomeUseCase(fakeFinanceRepositoryImpl)
    private var deleteUseCase = DeleteIncomeUseCase(fakeFinanceRepositoryImpl)
    private var editIncomeViewModel =
        EditIncomeViewModel(
            getIncomeUseCase = getIncomeUseCase,
            editIncomeUseCase = editIncomeUseCase,
            deleteUseCase = deleteUseCase,
        )

    @Test
    fun `Call Create but show error`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(),
            ) {
                expectInitialState()
                containerHost.edit()
                expectState {
                    copy(
                        showError = true,
                    )
                }
            }
        }

    @Test
    fun `Call Create success`() =
        runTest {
            val state =
                EditIncomeScreenState(
                    note = "note",
                    dateInMillis = 1000L,
                    amount = 100,
                )
            editIncomeViewModel.test(
                this,
                state,
            ) {
                expectInitialState()
                containerHost.edit()
                expectState {
                    copy(
                        showLoading = true,
                    )
                }
                expectSideEffect(GenericState.Success(Unit))
            }
        }

    @Test
    fun `Show Loading`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(),
            ) {
                expectInitialState()
                containerHost.showLoading()
                expectState {
                    copy(
                        showLoading = true,
                    )
                }
            }
        }

    @Test
    fun `Call Create Income Function`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(),
            ) {
                expectInitialState()
                containerHost.editIncome(1L)
                expectSideEffect(GenericState.Success(Unit))
            }
        }

    @Test
    fun `Set Note`() =
        runTest {
            val setNote = "note"
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(),
            ) {
                expectInitialState()
                containerHost.setNote(setNote)
                expectState {
                    copy(
                        note = setNote,
                    )
                }
            }
        }

    @Test
    fun `Set Date`() =
        runTest {
            val date = 1000L
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.setDate(date)
                expectState {
                    copy(
                        dateInMillis = date,
                        date = date.toStringDateFormat(),
                    )
                }
            }
        }

    @Test
    fun `Set Amount`() =
        runTest {
            val amount = "100"
            val amountInteger = amount.toLong()
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.setAmount(amount)
                expectState {
                    copy(
                        amount = amountInteger,
                        amountField = amountInteger.toMoneyFormat(),
                    )
                }
            }
        }

    @Test
    fun `Shot Date Error true`() =
        runTest {
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.showDateError(true)
                expectState {
                    copy(
                        showDateError = true,
                    )
                }
            }
        }

    @Test
    fun `Shot Date Error false`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(showDateError = true),
            ) {
                expectInitialState()
                containerHost.showDateError(false)
                expectState {
                    copy(
                        showDateError = false,
                    )
                }
            }
        }

    @Test
    fun `Shot Note Error true`() =
        runTest {
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.showNoteError(true)
                expectState {
                    copy(
                        showNoteError = true,
                    )
                }
            }
        }

    @Test
    fun `Shot Note Error false`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(
                    showNoteError = true,
                ),
            ) {
                expectInitialState()
                containerHost.showNoteError(false)
                expectState {
                    copy(
                        showNoteError = false,
                    )
                }
            }
        }

    @Test
    fun `Show Error true`() =
        runTest {
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.showError(true)
                expectState {
                    copy(
                        showError = true,
                    )
                }
            }
        }

    @Test
    fun `Show Error false`() =
        runTest {
            editIncomeViewModel.test(
                this,
                EditIncomeScreenState(showError = true),
            ) {
                expectInitialState()
                containerHost.showError(false)
                expectState {
                    copy(
                        showError = false,
                    )
                }
            }
        }

    @Test
    fun `Delete Income`() =
        runTest {
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                expectInitialState()
                containerHost.delete()
                expectSideEffect(GenericState.Success(Unit))
            }
        }

    @Test
    fun `Get Income`() =
        runTest {
            editIncomeViewModel.test(this, EditIncomeScreenState()) {
                val amount = incomeFinanceModelOne.amount
                expectInitialState()
                containerHost.getIncome(incomeFinanceModelOne.id)
                expectState {
                    copy(
                        initialDataLoaded = true,
                        id = incomeFinanceModelOne.id,
                        monthKey = incomeFinanceModelOne.monthKey,
                    )
                }
                expectState {
                    copy(
                        amountField = amount.toMoneyFormat(),
                        amount = amount,
                    )
                }
                expectState {
                    copy(
                        dateInMillis = incomeFinanceModelOne.localDateTime.toMillis(),
                        date = incomeFinanceModelOne.localDateTime.toMillis().toStringDateFormat(),
                    )
                }
                expectState {
                    copy(
                        note = incomeFinanceModelOne.note,
                    )
                }
            }
        }
}
