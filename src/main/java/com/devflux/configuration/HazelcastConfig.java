package com.devflux.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@Configuration
public class HazelcastConfig {

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance hazelcastInstance() {

        try {
            ClassPathResource resource =
                    new ClassPathResource("hazelcast.xml");

            if (!resource.exists()) {
                throw new RuntimeException(
                    "hazelcast.xml not found in src/main/resources"
                );
            }

            try (InputStream inputStream = resource.getInputStream()) {

                Config config =
                        new XmlConfigBuilder(inputStream).build();

                HazelcastInstance instance =
                        Hazelcast.newHazelcastInstance(config);

                System.out.println(
                    "Hazelcast started: "
                    + instance.getName()
                );

                System.out.println(
                    "Hazelcast running: "
                    + instance.getLifecycleService().isRunning()
                );

                return instance;
            }

        } catch (IOException e) {
            throw new RuntimeException(
                "Unable to load hazelcast.xml", e
            );
        }
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }
}