package me.kofesst.testtask.domain

interface GeneratorsRepository {
    fun getAllGenerators(): List<NumberGenerator>
}