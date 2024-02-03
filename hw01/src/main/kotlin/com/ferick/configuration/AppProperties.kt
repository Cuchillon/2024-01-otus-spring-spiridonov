package com.ferick.configuration

data class AppProperties(
    override val testFileName: String
) : TestFileNameProvider
