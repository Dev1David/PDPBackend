package com.example.PDPMobileGame.entity;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(unique = true,name = "email")
    private String email;
    @Column(name = "balance")
    private Integer balance;
    @Column(name="login_failed_attempts")
    private Integer login_failed_attempts;
    @Column(name="is_login_disabled")
    private Boolean is_login_disabled;
    @Column(name="token")
    private String token;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "role_id")
    private RoleEntity role;


    public UserEntity() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getLoginFailedAttempts() {
        return login_failed_attempts;
    }

    public void setLoginFailedAttempts(Integer login_failed_attempts) {
        this.login_failed_attempts = login_failed_attempts;
    }

    public Boolean getIsLoginDisabled() {
        return is_login_disabled;
    }

    public void setIsLoginDisabled(Boolean is_login_disabled) {
        this.is_login_disabled = is_login_disabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", login_failed_attempts=" + login_failed_attempts +
                ", is_login_disabled=" + is_login_disabled +
                ", token='" + token + '\'' +
                ", role=" + role +
                '}';
    }
}
