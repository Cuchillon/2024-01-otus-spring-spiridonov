package com.ferick.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import java.util.Locale

@ConfigurationProperties(prefix = "test")
class AppProperties @ConstructorBinding constructor(
    override val rightAnswersCountToPass: Int,
    locale: String,
    private val fileNameByLocaleTag: Map<String, String>
) : TestConfig, TestFileNameProvider, LocaleConfig {

    override val locale: Locale = Locale.forLanguageTag(locale)

    override val testFileName: String
        get() = fileNameByLocaleTag[locale.toLanguageTag()]
            ?: throw IllegalArgumentException("There is no file name for locale ${locale.toLanguageTag()}")
}
