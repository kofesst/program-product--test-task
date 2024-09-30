package me.kofesst.testtask.domain

import java.math.BigInteger

interface NumberGenerator {
    val name: String
    val numbersSequence: Sequence<BigInteger>
}