package presentation.viewmodel.categorymonthdetail

import com.carlosgub.myfinances.core.utils.getCurrentMonthKey
import data.repository.impl.FakeFinanceRepositoryImpl
import domain.model.CategoryEnum
import domain.usecase.GetExpenseMonthDetailUseCase
import domain.usecase.GetIncomeMonthDetailUseCase
import kotlinx.coroutines.test.runTest
import mock.expenseScreenModelOne
import mock.monthExpenseDetailScreenModel
import mock.monthIncomeDetailScreenModel
import org.orbitmvi.orbit.test.test
import kotlin.test.Test

class CategoryMonthDetailViewModelTest {
    private val fakeFinanceRepositoryImpl = FakeFinanceRepositoryImpl()
    private var getExpenseMonthDetailUseCase =
        GetExpenseMonthDetailUseCase(fakeFinanceRepositoryImpl)
    private var getIncomeMonthDetailUseCase = GetIncomeMonthDetailUseCase(fakeFinanceRepositoryImpl)
    private var categoryMonthDetailViewModel =
        CategoryMonthDetailViewModel(
            getExpenseMonthDetailUseCase = getExpenseMonthDetailUseCase,
            getIncomeMonthDetailUseCase = getIncomeMonthDetailUseCase,
        )

    @Test
    fun `Get Months Detail Expense`() =
        runTest {
            categoryMonthDetailViewModel.test(this, CategoryMonthDetailScreenState()) {
                expectInitialState()
                containerHost.getMonthDetail()
                expectState {
                    copy(
                        showLoading = true,
                    )
                }
                expectState {
                    copy(
                        monthDetail = monthExpenseDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Get Months Detail Income`() =
        runTest {
            categoryMonthDetailViewModel.test(
                this,
                CategoryMonthDetailScreenState(
                    category = CategoryEnum.WORK,
                ),
            ) {
                expectInitialState()
                containerHost.getMonthDetail()
                expectState {
                    copy(
                        showLoading = true,
                    )
                }
                expectState {
                    copy(
                        monthDetail = monthIncomeDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Observe Expense`() =
        runTest {
            val state = CategoryMonthDetailScreenState()
            categoryMonthDetailViewModel.test(
                this,
                state,
            ) {
                expectInitialState()
                containerHost.observeExpense(
                    categoryEnum = state.category,
                    monthKey = state.monthKey,
                )
                expectState {
                    copy(
                        monthDetail = monthExpenseDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Observe Income`() =
        runTest {
            val state = CategoryMonthDetailScreenState()
            categoryMonthDetailViewModel.test(
                this,
                state,
            ) {
                expectInitialState()
                containerHost.observeIncome(
                    monthKey = state.monthKey,
                )
                expectState {
                    copy(
                        monthDetail = monthIncomeDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Navigate To Edit Expense`() =
        runTest {
            categoryMonthDetailViewModel.test(this, CategoryMonthDetailScreenState()) {
                expectInitialState()
                containerHost.navigateToEditExpense(expenseScreenModelOne)
                expectSideEffect(
                    CategoryMonthDetailScreenSideEffect.NavigateToMonthDetail(
                        expenseScreenModelOne,
                    ),
                )
            }
        }

    @Test
    fun `Set Initial Configuration`() =
        runTest {
            categoryMonthDetailViewModel.test(this, CategoryMonthDetailScreenState()) {
                expectInitialState()
                containerHost.setInitialConfiguration(
                    monthKey = getCurrentMonthKey(),
                    category = CategoryEnum.WORK.name,
                )
                expectState {
                    copy(
                        monthKey = getCurrentMonthKey(),
                        category = CategoryEnum.WORK,
                    )
                }
                expectState {
                    copy(
                        showLoading = true,
                    )
                }
                expectState {
                    copy(
                        monthDetail = monthIncomeDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Set Month Detail Screen Model`() =
        runTest {
            categoryMonthDetailViewModel.test(
                this,
                CategoryMonthDetailScreenState(),
            ) {
                expectInitialState()
                containerHost.setMonthDetailScreenModel(monthExpenseDetailScreenModel)
                expectState {
                    copy(
                        monthDetail = monthExpenseDetailScreenModel,
                        showLoading = false,
                        isInitialDataLoaded = true,
                    )
                }
            }
        }

    @Test
    fun `Show Loading`() =
        runTest {
            categoryMonthDetailViewModel.test(
                this,
                CategoryMonthDetailScreenState(),
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
}
