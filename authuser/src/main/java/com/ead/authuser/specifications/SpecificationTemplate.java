package com.ead.authuser.specifications;

import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

// Funciona no JDK11 mas não no 17, no 17 precisa adicionar o argumento --ilegal-access=permit na JVM
// Usa Reflection
public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}

}
