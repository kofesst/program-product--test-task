package me.kofesst.testtask.data

import me.kofesst.testtask.domain.NumberGenerator
import java.math.BigInteger

object PrimeNumberGenerator : NumberGenerator {
    override val name: String = "Простые числа"

    // 2 to [3, 5, 7, 9, 11, 13, 15, 17, ...]
    // 3 to [5, 7, 11, 13, 17, ...]
    // 5 to [7, 11, 13, 17, ...]
    // 7 to [11, 13, 17, ...]
    // 11 to [13, 17, ...]
    // 13 to [17, ...]
    // 17 to [...]
    override val numbersSequence: Sequence<BigInteger> =
        generateSequence(
            seed = 2L.toBigInteger() to
                    generateSequence(3L.toBigInteger()) { it.plus(2.toBigInteger()) }
        ) {
            val currentPrimesIterator = it.second.iterator()
            val nextPrime = currentPrimesIterator.next()
            // Фильтрация последовательности кандидатов - если они не кратны текущему простому числу
            nextPrime to currentPrimesIterator.asSequence().filter { primeCandidate ->
                primeCandidate % nextPrime != BigInteger.valueOf(0L)
            }
        }.map { it.first }
}