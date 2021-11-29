package com.ead.course.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_USERS")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseUserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id")
    @NonNull
    private CourseModel course;

    @Column(nullable = false)
    @NonNull
    private UUID userId;
}
