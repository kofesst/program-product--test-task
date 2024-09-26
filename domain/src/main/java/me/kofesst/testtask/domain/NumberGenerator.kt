package me.kofesst.testtask.domain

interface NumberGenerator {
    val name: String
    val numbersSequence: Sequence<Long>
}