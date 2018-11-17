package com.elasticDataQuery.configuration;

import com.elasticDataQuery.common.YamlPropertyLoaderFactory;
import com.elasticDataQuery.repository.FileDataRepositoryImpl;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@Configuration
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@EnableElasticsearchRepositories(basePackages = "com.elasticDataLoader")
@PropertySource(value = "classpath:application-test.yml", factory = YamlPropertyLoaderFactory.class)
@ComponentScan(basePackages = { "com.elasticDataLoader" }, excludeFilters = {
        @ComponentScan.Filter(type = ASSIGNABLE_TYPE,
                value = {
                        ElasticSearchConfig.class,
                        ElasticsearchAutoConfiguration.class
                })
})
@OverrideAutoConfiguration(enabled=true)
public class ElasticDataLoaderTestConfig extends ElasticSearchTestConfig{


    @Bean
    public FileDataRepositoryImpl fileDataRepository(ElasticsearchTemplate elasticsearchTemplate){
        return new FileDataRepositoryImpl(elasticsearchTemplate);
    }



}
