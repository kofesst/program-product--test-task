package me.kofesst.testtask.data

import org.junit.Test

class NumberGeneratorTest {
    private val fibonacciExpectedValues = mapOf<Int, Long>(
        // Index to Expected
        0 to 1,
        1 to 1,
        2 to 2,
        7 to 21,
        14 to 610,
        20 to 10946,
        21 to 17711,
        40 to 165580141,
        60 to 2504730781961,
        80 to 37889062373143906
    )

    @Test
    fun `test FibonacciNumberGenerator returns correct sequence`() {
        val fibonacciSequence = FibonacciNumberGenerator.numbersSequence
        fibonacciExpectedValues.forEach { (index, expected) ->
            val actual = fibonacciSequence.take(index + 1).last()
            assert(expected == actual) {
                "Expected fibonacci number at index $index - $expected, but actual - $actual"
            }
        }
        val expectedLastItem = FibonacciNumberGenerator.MAX_FIBONACCI_FOR_LONG_TYPE
        val actualLastItem = fibonacciSequence.last()
        assert(expectedLastItem == actualLastItem) {
            "Expected last fibonacci number - $expectedLastItem, but actual - $actualLastItem"
        }
    }

    private val primeExpectedValues = mapOf<Int, Long>(
        // Index to Expected
        0 to 2,
        1 to 3,
        2 to 5,
        3 to 7,
        4 to 11,
        5 to 13,
        20 to 73,
        40 to 179,
        60 to 283
    )

    @Test
    fun `test PrimeNumberGenerator returns correct sequence`() {
        val primesSequence = PrimeNumberGenerator.numbersSequence
        primeExpectedValues.forEach { (index, expected) ->
            val actual = primesSequence.take(index + 1).last()
            assert(expected == actual) {
                "Expected prime number at index $index - $expected, but actual - $actual"
            }
        }
    }
}