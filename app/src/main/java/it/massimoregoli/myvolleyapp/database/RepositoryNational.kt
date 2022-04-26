package it.massimoregoli.myvolleyapp.database

import androidx.lifecycle.LiveData
import it.massimoregoli.myvolleyapp.model.National

class RepositoryNational(private val daoNational:NationalDao) {
    val allProverbs: LiveData<List<National>> = daoNational.getAll()
}