package com.ead.course.services.impl;

import com.ead.course.dtos.NotificationCommandDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.publishers.NotificationCommandPublisher;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.repositories.UserRepository;
import com.ead.course.services.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    private final UserRepository userRepository;

    private final NotificationCommandPublisher notificationPublisher;


//    @Override
//    @Transactional
//    public void delete(CourseModel courseModel) {
//        var modules = courseModel.getModules();
//
//        modules.forEach(module -> {
//            lessonRepository.deleteAll(module.getLessons());
//        });
//
//        moduleRepository.deleteAll(modules);
//
//        courseRepository.delete(courseModel);
//
//    }

    @Override
    @Transactional
    public void delete(CourseModel courseModel) {
        var modules = moduleRepository.findAllByCourseCourseId(courseModel.getCourseId());

        if (!modules.isEmpty()) {
            for (var module : modules) {
                var lessonModelList = lessonRepository.findAllLessons(module.getModuleId());
                if (!lessonModelList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModelList);
                }
            }

            moduleRepository.deleteAll(modules);
        }

        courseRepository.deleteCourseByCourse(courseModel.getCourseId());
        courseRepository.delete(courseModel);
    }

    @Override
    public void save(CourseModel courseModel) {
        courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Page<CourseModel> findAll(Pageable pageable, Specification<CourseModel> spec) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Override
    @Transactional
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        courseRepository.saveCourseUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourseAndSendNotification(CourseModel course, UserModel user) {
        courseRepository.saveCourseUser(course.getCourseId(), user.getId());

        try {
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTitle("Bem vindo(a) ao curso: " + course.getName());
            notificationCommandDto.setMessage(user.getFullName() + " a sua inscrição foi realizada com sucesso.");
            notificationCommandDto.setUserId(user.getId());
            notificationPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

}
