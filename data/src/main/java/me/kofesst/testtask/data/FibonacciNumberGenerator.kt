package me.kofesst.testtask.data

import me.kofesst.testtask.domain.NumberGenerator
import java.math.BigInteger

object FibonacciNumberGenerator : NumberGenerator {
    private val INITIAL_FIBONACCI_NUMBERS = BigInteger.valueOf(0L) to BigInteger.valueOf(1L)

    override val name: String = "Числа Фибоначчи"

    // (1, 1) - (1, 2) - (2, 3) - (3, 5) - (5, 8) - (8, 13) - пары
    //   1    -   1    -   2    -   3    -   5    -   8     - значения
    override val numbersSequence: Sequence<BigInteger> =
        generateSequence(seed = INITIAL_FIBONACCI_NUMBERS) { (previousTwo, previousOne) ->
            previousOne to (previousOne + previousTwo)
        }.map { (_, previousOne) -> previousOne }
}