package com.elasticDataQuery.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;


@Configuration
@OverrideAutoConfiguration(enabled=true)
@ComponentScan(basePackages = { "com.elasticDataLoader.repository" }/*, excludeFilters = {
        @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        ElasticSearchConfig.class,
                        ElasticsearchAutoConfiguration.class
                })
}*/)
public class ElasticSearchTestConfig {

    Logger log = LoggerFactory.getLogger(ElasticSearchTestConfig.class);

    @Value("${spring.data.elasticsearch.port}")
    private int esPort;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String esClusterName;

    @Bean
    public Client embeddedClient() throws Exception {

        Settings settings = Settings.builder().put("cluster.name", esClusterName).build();
        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), esPort));

    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        log.info("elasticsearchTemplate building initiated");

        ElasticsearchTemplate elasticsearchTemplate =
                new ElasticsearchTemplate(embeddedClient());
        log.info("elasticsearchTemplate build completed");

        return elasticsearchTemplate;
    }

}
