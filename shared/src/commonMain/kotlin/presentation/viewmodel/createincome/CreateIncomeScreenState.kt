package presentation.viewmodel.createincome

import com.carlosgub.myfinances.core.utils.toMoneyFormat
import domain.model.CategoryEnum

data class CreateIncomeScreenState(
    val category: CategoryEnum = CategoryEnum.FOOD,
    val amountField: String = 0.0.toMoneyFormat(),
    val amount: Double = 0.0,
    val showDateError: Boolean = false,
    val showNoteError: Boolean = false,
    val showError: Boolean = false,
    val note: String = "",
    val date: String = "",
    val dateInMillis: Long = 0L,
    val showLoading: Boolean = false,
)
