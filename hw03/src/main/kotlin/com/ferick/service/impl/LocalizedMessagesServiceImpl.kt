package com.ferick.service.impl

import com.ferick.configuration.properties.LocaleConfig
import com.ferick.service.LocalizedMessagesService
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class LocalizedMessagesServiceImpl(
    private val messageSource: MessageSource,
    private val localeConfig: LocaleConfig
) : LocalizedMessagesService {

    override fun getMessage(code: String, args: Array<Any>): String {
        return messageSource.getMessage(code, args, localeConfig.locale)
    }
}
