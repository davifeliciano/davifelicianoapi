package br.edu.infnet.davifelicianoapi.model.clients.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;

import feign.Request;

public class FeriadosFeignClientConfig {

    @Bean
    public Request.Options requestOptions() {
        int connectTimeoutMillis = 5000;
        int readTimeoutMillis = 5000;
        boolean followRedirects = false;
        return new Request.Options(connectTimeoutMillis, TimeUnit.MILLISECONDS, readTimeoutMillis,
                TimeUnit.MILLISECONDS, followRedirects);
    }

}
