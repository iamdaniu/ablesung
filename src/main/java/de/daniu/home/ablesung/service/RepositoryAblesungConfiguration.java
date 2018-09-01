package de.daniu.home.ablesung.service;

import de.daniu.home.ablesung.AblesungsArt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryAblesungConfiguration {
    @Bean
    public RepositoryAblesungService waterService() {
        return new RepositoryAblesungService(AblesungsArt.WATER);
    }
    @Bean
    public RepositoryAblesungService electricityService() {
        return new RepositoryAblesungService(AblesungsArt.ELECTRICITY);
    }
    @Bean
    public RepositoryAblesungService heating1Service() {
        return new RepositoryAblesungService(AblesungsArt.HEATING_1);
    }
    @Bean
    public RepositoryAblesungService heating2Service() {
        return new RepositoryAblesungService(AblesungsArt.HEATING_2);
    }
}
