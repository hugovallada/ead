package com.ead.authuser.models;

import com.ead.authuser.enums.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleModel implements GrantedAuthority, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    RoleType roleName;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }
}
