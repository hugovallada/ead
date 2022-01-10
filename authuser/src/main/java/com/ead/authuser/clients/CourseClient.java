package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.service.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    UtilsService utilsService;

    @Value("${ead.api.url.course}")
    String REQUEST_URI_COURSE;

    // Retry pode ser perigoso, pois vai aumentar o número de requests
    // @Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "retryFallback")
    public Page<CourseDto> getAllCoursesById(UUID userId, Pageable pageable, String token) {
        List<CourseDto> searchResult = null;

        String url = REQUEST_URI_COURSE + utilsService.createUrl(userId, pageable);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<>("parameters", httpHeaders);

        log.info("Request URL: {}", url);

        try {
            var responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };

            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
            searchResult = result.getBody().getContent();

        } catch (HttpStatusCodeException ex) {
            log.error("Ending request / courses userId {}", userId);
        }

        return new PageImpl<>(searchResult);
    }

    // Deve ter os mesmos parametros + uma exceção e mesmo tipo de retorno
    public Page<CourseDto> retryFallback(UUID userId, Pageable pageable, Throwable throwable) {
        log.error("Retry Fallback, cause {}", throwable.toString());
        return new PageImpl<>(new ArrayList<>());
    }
}
