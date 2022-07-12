package com.cafe.entity.user;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ROLE_ID_SEQUENCE")
    @SequenceGenerator(name = "USER_ROLE_ID_SEQUENCE")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",
                foreignKey = @ForeignKey(name = "USER_ROLE_USER_USER_ID"),
                nullable = false,
                referencedColumnName = "id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role_type", nullable = false, length = 20)
    private UserRoleType userRoleType;

    public UserRole(User user, UserRoleType userRoleType) {
        setUser(user);
        setUserRoleType(userRoleType);
    }

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "id should not be null");
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        Assert.notNull(user, "user should not be null");
        this.user = user;
    }

    public UserRoleType getUserRoleType() {
        return userRoleType;
    }

    public void setUserRoleType(UserRoleType userRoleType) {
        Assert.notNull(userRoleType, "user role type should not be null");
        this.userRoleType = userRoleType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id) && Objects.equals(user, userRole.user) && userRoleType == userRole.userRoleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, userRoleType);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                //", user=" + user +
                ", userRoleType=" + userRoleType +
                '}';
    }
}
