package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    String REQUEST_URI = "http://localhost:8082";

    public Page<CourseDto> getAllCoursesById(UUID userId, Pageable pageable) {
        List<CourseDto> searchResult = null;

        String url = REQUEST_URI + "/courses?userId=" + userId + "&page=" + pageable.getPageNumber() +
                "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(":", ",");

        log.info("Request URL: {}", url);

        try {

        } catch (HttpStatusCodeException ex) {
            log.error("Ending request / courses userId {}", userId);
        }

        return null;
    }

}
