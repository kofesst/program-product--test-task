package me.kofesst.testtask.data.impl

import me.kofesst.testtask.data.FibonacciNumberGenerator
import me.kofesst.testtask.data.PrimeNumberGenerator
import me.kofesst.testtask.domain.GeneratorsRepository
import me.kofesst.testtask.domain.NumberGenerator

class GeneratorsRepositoryImpl : GeneratorsRepository {
    override fun getAllGenerators(): List<NumberGenerator> {
        return listOf(
            PrimeNumberGenerator,
            FibonacciNumberGenerator
        )
    }
}