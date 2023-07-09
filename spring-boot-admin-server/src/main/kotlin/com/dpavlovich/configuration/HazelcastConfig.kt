package com.dpavlovich.configuration

import com.hazelcast.config.*
import com.hazelcast.spi.merge.PutIfAbsentMergePolicy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HazelcastConfig {
    @Bean
    fun hazelcast(): Config {
        val eventStoreMap: MapConfig = MapConfig("spring-boot-admin-event-store")
            .setInMemoryFormat(InMemoryFormat.OBJECT)
            .setBackupCount(1)
            .setEvictionConfig(EvictionConfig().setEvictionPolicy(EvictionPolicy.NONE))
            .setMergePolicyConfig(MergePolicyConfig(PutIfAbsentMergePolicy::class.java.name, 100))
        val sentNotificationsMap: MapConfig = MapConfig("spring-boot-admin-application-store")
            .setInMemoryFormat(InMemoryFormat.OBJECT)
            .setBackupCount(1)
            .setEvictionConfig(EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU))
            .setMergePolicyConfig(MergePolicyConfig(PutIfAbsentMergePolicy::class.java.name, 100))
        val config = Config()
        config.addMapConfig(eventStoreMap)
        config.addMapConfig(sentNotificationsMap)
        config.setProperty("hazelcast.jmx", "true")
        config.networkConfig
            .join
            .multicastConfig
            .setEnabled(false)
        val tcpIpConfig: TcpIpConfig = config.networkConfig
            .join
            .tcpIpConfig
        tcpIpConfig.setEnabled(true)
        tcpIpConfig.setMembers(listOf("127.0.0.1"))
        return config
    }
}