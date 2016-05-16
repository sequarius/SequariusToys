package com.sequarius.microblog.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by Sequarius on 2015/5/31.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long  serialVersionUID    = -2602075959996355784L;


    @Id
    @GenericGenerator(name="generator",strategy="increment")
    @GeneratedValue(generator="generator")
    private long id;
    @Length(min=0,max = 20,message = "{user.username.length.illegal}")
    @Column(length = 20,nullable = false,unique = true)
    private String username;
    @Length(min=6,max = 16,message="{user.password.length.illegal}")
    @Column(length = 16,nullable = false)
    private String password;
    @Column(length = 70)
    private String address;


    @Email(message = "{user.mail.format.illegal}")
    @NotEmpty(message ="{user.mail.empty.illegal}")
    @Column(length = 128,nullable = false,unique = true)
    private String email;
    @Column(length = 128)
    private String avatar;
    @Column(name = "phone_num",length = 20)
    @Pattern(regexp = "[1][34578]\\d{9}", message = "{user.phone.leagl.error}")
    private String phoneNum;
    @Column(name = "regist_time")
    private long registTime;
    @Column(name = "last_login")
    private long lastLogin;
    @Column(name ="last_login_ip",length = 15)
    private String lastLoginIp;
    @Column(length = 1)
    @Pattern(regexp = "['男''女']", message = "{user.gender.leagl.error}")
    private String gender;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getRegistTime() {
        return registTime;
    }

    public void setRegistTime(long registTime) {
        this.registTime = registTime;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", registTime=" + registTime +
                ", lastLogin=" + lastLogin +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
