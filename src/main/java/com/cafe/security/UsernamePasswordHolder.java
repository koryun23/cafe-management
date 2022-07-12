package com.cafe.security;

import java.util.Objects;

public class UsernamePasswordHolder {

    private String username;
    private String password;

    public UsernamePasswordHolder(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UsernamePasswordHolder() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsernamePasswordHolder that = (UsernamePasswordHolder) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "UsernamePasswordHolder{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
