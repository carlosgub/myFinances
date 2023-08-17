package presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.CategoryEnum
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import utils.toMoneyFormat

class CreateExpenseViewModel : ViewModel() {

    private val _category = MutableStateFlow(CategoryEnum.FOOD)
    val category = _category.asStateFlow()
    private val _amountField = MutableStateFlow(0.0.toMoneyFormat())
    val amountField = _amountField.asStateFlow()
    private val _amountValueField = MutableStateFlow(0.0)


    fun setCategory(categoryEnum: CategoryEnum) {
        viewModelScope.launch {
            _category.emit(categoryEnum)
        }
    }

    fun amountFieldChange(textFieldValue: String) {
        if (textFieldValue != _amountField.value) {
            val cleanString: String =
                textFieldValue.replace("""[$,.]""".toRegex(), "").trim().trimStart('0')
            val parsed = if (cleanString.isBlank()) {
                0.0
            } else {
                cleanString.toDouble()
            }
            val amount = parsed / 100

            viewModelScope.launch {
                _amountField.emit(amount.toMoneyFormat())
                _amountValueField.emit(amount)
            }
        }
    }

    fun createExpense() {
        TODO("Not yet implemented")
    }
}