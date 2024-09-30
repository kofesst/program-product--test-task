package me.kofesst.testtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import me.kofesst.testtask.domain.GeneratorsRepository
import me.kofesst.testtask.domain.NumberGenerator
import java.math.BigInteger
import javax.inject.Inject

@HiltViewModel
class GeneratorsViewModel @Inject constructor(
    private val generatorsRepository: GeneratorsRepository
) : ViewModel() {
    private val _generatorsFlow = MutableStateFlow<List<NumberGenerator>>(emptyList())
    private val _selectedGeneratorFlow = MutableStateFlow<NumberGenerator?>(null)
    private val _numbersVisibleSizeFlow = MutableStateFlow<Int>(50)

    val selectedGenerator: StateFlow<NumberGenerator?> = _selectedGeneratorFlow.asStateFlow()
    val generators: StateFlow<List<NumberGenerator>> = _generatorsFlow.asStateFlow()

    val numbersFlow: Flow<List<BigInteger>> =
        combine(
            flow = _selectedGeneratorFlow.filterNotNull(),
            flow2 = _numbersVisibleSizeFlow
        ) { generator, size ->
            generator
                .numbersSequence
                .take(size)
                .toList()
        }

    fun loadGenerators() {
        viewModelScope.launch {
            val allGenerators = generatorsRepository.getAllGenerators()
            _generatorsFlow.emit(allGenerators)
            _selectedGeneratorFlow.emit(allGenerators.last())
        }
    }

    fun selectGenerator(generator: NumberGenerator) {
        viewModelScope.launch {
            _selectedGeneratorFlow.emit(generator)
        }
    }

    fun increaseNumbersVisibleSize() {
        viewModelScope.launch {
            _numbersVisibleSizeFlow.emit(
                _numbersVisibleSizeFlow.value + 50
            )
        }
    }
}