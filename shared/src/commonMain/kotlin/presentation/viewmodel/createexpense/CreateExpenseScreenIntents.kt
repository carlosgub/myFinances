package presentation.viewmodel.createexpense

import domain.model.CategoryEnum
import kotlinx.coroutines.Job

interface CreateExpenseScreenIntents {
    fun setCategory(categoryEnum: CategoryEnum): Job

    fun setAmount(textFieldValue: String): Job

    fun showDateError(boolean: Boolean): Job

    fun showNoteError(boolean: Boolean): Job

    fun showError(boolean: Boolean): Job

    fun setNote(note: String): Job

    fun setDate(date: Long): Job

    fun create(): Job
}
