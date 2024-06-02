package com.ferick.configuration

import com.ferick.model.dto.TerminatorOrderItem
import com.ferick.model.dto.TerminatorType
import com.ferick.model.entities.Terminator
import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.MessageChannelSpec
import org.springframework.integration.dsl.MessageChannels
import org.springframework.integration.dsl.PollerSpec
import org.springframework.integration.dsl.Pollers
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.jpa.dsl.Jpa
import org.springframework.integration.jpa.support.PersistMode
import org.springframework.integration.scheduling.PollerMetadata


@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = ["com.ferick.service"])
class IntegrationConfiguration(
    private val entityManagerFactory: EntityManagerFactory
) {

    @Bean
    fun itemsChannel(): MessageChannelSpec<*, *> {
        return MessageChannels.queue(10)
    }

    @Bean
    fun terminatorChannel(): MessageChannelSpec<*, *> {
        return MessageChannels.publishSubscribe()
    }

    @Bean(name = [PollerMetadata.DEFAULT_POLLER])
    fun poller(): PollerSpec {
        return Pollers.fixedRate(100).maxMessagesPerPoll(3)
    }

    @Bean
    fun routingFlow(): IntegrationFlow {
        return integrationFlow("itemsChannel") {
            route<TerminatorOrderItem, TerminatorType>(
                { it.type }
            ) {
                this.subFlowMapping(TerminatorType.T_800) { sf ->
                    sf.channel("t800BuildingChannel")
                }
                this.subFlowMapping(TerminatorType.T_1000) { sf ->
                    sf.channel("t1000BuildingChannel")
                }
            }
        }
    }

    @Bean
    fun t800Flow(): IntegrationFlow {
        return integrationFlow("t800BuildingChannel") {
            handle("t800AssemblingService", "assemble")
            channel("terminatorChannel")
        }
    }

    @Bean
    fun t1000Flow(): IntegrationFlow {
        return integrationFlow("t1000BuildingChannel") {
            handle("t1000AssemblingService", "assemble")
            channel("terminatorChannel")
        }
    }

    @Bean
    fun outboundFlow(): IntegrationFlow {
        return integrationFlow("terminatorChannel") {
            handle(
                Jpa.outboundAdapter(entityManagerFactory)
                    .entityClass(Terminator::class.java)
                    .persistMode(PersistMode.PERSIST)
            ) {
                this.transactional()
            }
        }
    }
}
