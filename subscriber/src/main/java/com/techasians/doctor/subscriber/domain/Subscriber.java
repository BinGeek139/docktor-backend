package com.techasians.doctor.subscriber.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Subscribe class.
 *
 * @author ngocquang
 * @since 20/1/2020
 */


@Table
@Entity(name = "subscriber")
public class Subscriber extends AbstractAuditingEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    private Integer status;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "dob")
    private Date dob;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "idCard")
    private String idCard;
    @Column(name = "address")
    private String address;
    @Column(name = "token")
    private String token;
    @Column(name = "otp")
    private String otp;
    @Column(name = "expire_otp_date")
    private Timestamp expireOTPDate;
    @Column(name = "email")
    String email;
    @Column(name = "flag")
    Integer flag;
    @Column(name = "id_facebook")
    String idFacebook;
    @Column(name = "id_google")
    String idGoogle;
    @Column(name = "role")
    String role;
    @Column(name = "description")
    String description;
    @Column(name = "link_avatar")

    String linkAvatar;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public Subscriber() {
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String tokenResetPassword) {
        this.otp = tokenResetPassword;
    }

    public Timestamp getExpireOTPDate() {
        return expireOTPDate;
    }

    public void setExpireOTPDate(Timestamp expireTokenResetPassword) {
        this.expireOTPDate = expireTokenResetPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
