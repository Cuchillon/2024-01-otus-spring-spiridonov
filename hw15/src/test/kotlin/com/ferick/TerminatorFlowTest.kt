package com.ferick

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ferick.model.dto.OrderDetails
import com.ferick.model.dto.T1000Construction
import com.ferick.model.dto.T800Construction
import com.ferick.model.dto.TerminatorOrderRequest
import com.ferick.model.dto.TerminatorOrderResponse
import com.ferick.model.dto.TerminatorType
import com.ferick.repositories.TerminatorRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.concurrent.TimeUnit

@SpringBootTest
@AutoConfigureMockMvc
class TerminatorFlowTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var terminatorRepository: TerminatorRepository

    @Test
    fun testFlow() {
        val response = mockMvc
            .perform(
                MockMvcRequestBuilders.post("/terminator")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.objToString())
            )
            .andReturn().response
        val responseStatus = response.status
        val responseBody = response.contentAsString.toObj<TerminatorOrderResponse>()
        val terminators = checkUntilNotEmpty(3000L) {
            terminatorRepository.findByOrderId(responseBody.orderId)
        }
        val (t800List, t1000List) = terminators.partition { it.type == TerminatorType.T_800 }
        assertThat(responseStatus).isEqualTo(200)
        assertThat(t800List).hasSize(3)
        assertThat(t1000List).hasSize(1)
        assertThat(t800List.all { it.construction is T800Construction }).isTrue()
        assertThat(t1000List.all { it.construction is T1000Construction }).isTrue()
    }

    companion object {
        private val request = TerminatorOrderRequest().apply {
            order = setOf(
                OrderDetails().apply {
                    type = TerminatorType.T_800.name
                    quantity = 3
                },
                OrderDetails().apply {
                    type = TerminatorType.T_1000.name
                    quantity = 1
                }
            )
        }

        private fun <T> T.objToString(): String = jacksonObjectMapper().writeValueAsString(this)

        inline fun <reified T> String.toObj(): T = jacksonObjectMapper().readValue<T>(this)

        private fun <T> checkUntilNotEmpty(timeout: Long, block: () -> List<T>): List<T> {
            val end = System.currentTimeMillis() + timeout
            var result = emptyList<T>()
            while (System.currentTimeMillis() < end) {
                result = block.invoke()
                if (result.isNotEmpty()) {
                    return result
                }
                TimeUnit.MILLISECONDS.sleep(1000)
            }
            return result
        }
    }
}
