package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.service.UtilsService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class UserClient {

    @Autowired
    RestTemplate restTemplate;

    UtilsService utilsService;

    public Page<CourseDto> getAllCoursesById(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;

        String url = utilsService.createUrl(userId, pageable);

        log.info("Request URL: {}", url);

        try {
            var responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };

            ResponseEntity<ResponsePageDto<CourseDto>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

        } catch (HttpStatusCodeException ex) {
            log.error("Ending request / courses userId {}", userId);
        }

        return new PageImpl<>(searchResult);
    }

}
