package com.ead.course.clients;

import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;


    public Page<UserDto> getAllUsersByCourse(Pageable pageable, UUID courseId) {
        List<UserDto> searchResult = null;

        String url = utilsService.createUrl(courseId, pageable);

        try {
            var responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>(){
            };

            var result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();

        } catch (HttpStatusCodeException ex) {
            log.error("Ending request / users courseId {}", courseId);
        }

        return new PageImpl<>(searchResult);
    }

}
