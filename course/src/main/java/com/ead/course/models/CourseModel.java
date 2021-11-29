package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_COURSES")
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column
    private String imageUrl;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private OffsetDateTime updateDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseLevel courseLevel;

    @Column
    private UUID userInstructor;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // qnd for consulta, ele não mostra esse campo
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) // Define como os selects são feito (Select -> 1 select pro curso e 1 pra cada modulo; Join -> 1 única consulta pra pegar tudo; Subselect -> 2 consultas, 1 pra curso e 1 pra todos os módulos)
    @OnDelete(action = OnDeleteAction.CASCADE) // delega a responsabilidade para o banco, deletando todos os módulos de uma vez, com o fetch faria a deleção de 1 módulo por vez
    private Set<ModuleModel> modules; // FetchMode pode redefinir o FetchType. (JOIN -> Faz EAGER, e ignora o fetch type definido; SELECT OU SUBSELECT -> LAZY).

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // qnd for consulta, ele não mostra esse campo
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private Set<CourseUserModel> users;

    public CourseUserModel convertToCourseUserModel(UUID userId) {
        return new CourseUserModel(this, userId);
    }
}
