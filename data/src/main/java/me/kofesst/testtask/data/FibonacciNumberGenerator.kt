package me.kofesst.testtask.data

import me.kofesst.testtask.domain.NumberGenerator

object FibonacciNumberGenerator : NumberGenerator {
    const val MAX_FIBONACCI_FOR_LONG_TYPE = 7540113804746346429L

    override val name: String = "Числа Фибоначчи"

    // (1, 1) - (1, 2) - (2, 3) - (3, 5) - (5, 8) - (8, 13) - пары
    //   1    -   1    -   2    -   3    -   5    -   8     - значения
    override val numbersSequence: Sequence<Long> =
        generateSequence(Pair(0L, 1L)) { (previousTwo, previousOne) ->
            previousOne to (previousOne + previousTwo)
        }
            .takeWhile { (previousTwo, previousOne) ->
                previousTwo in 0..MAX_FIBONACCI_FOR_LONG_TYPE &&
                        previousOne in 0..MAX_FIBONACCI_FOR_LONG_TYPE
            }
            .map { (_, previousOne) -> previousOne }
}