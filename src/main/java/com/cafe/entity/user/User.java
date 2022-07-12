package com.cafe.entity.user;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE")
    @SequenceGenerator(name = "USER_ID_SEQUENCE")
    private Long id;

    @Column(name = "first_name", nullable = false, length = 40)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 40)
    private String secondName;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<UserRole> userRoleList;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column
    private String password;

    public User() {
    }

    public User(String firstName, String secondName, String username, String password, LocalDateTime createdAt) {
        setFirstName(firstName);
        setSecondName(secondName);
        setUsername(username);
        setPassword(password);
        setCreatedAt(createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Assert.notNull(id, "Id should not be null");
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        Assert.notNull(firstName, "First name should not be null");
        Assert.hasText(firstName, "First name should not be empty");
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        Assert.notNull(secondName, "Second name should not be null");
        Assert.hasText(secondName, "Second name should not be empty");
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Assert.notNull(username, "Username should not be null");
        Assert.hasText(username, "Username should not be empty");
        this.username = username;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        Assert.notNull(userRoleList, "User role list should not be null");
        this.userRoleList = userRoleList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        Assert.notNull(createdAt, "Creation date should not be null");
        this.createdAt = createdAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Assert.notNull(password, "password should not be null");
        Assert.hasText(password, "password should not be empty");
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(secondName, user.secondName) &&
                Objects.equals(username, user.username) &&
                Objects.equals(userRoleList, user.userRoleList) &&
                Objects.equals(createdAt, user.createdAt) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, username, userRoleList, createdAt, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", username='" + username + '\'' +
                ", userRoleList=" + userRoleList +
                ", createdAt=" + createdAt +
                '}';
    }
}
