package com.example.PDPMobileGame.entity;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
//@JoinColumn(name="ID_CATALOG")
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true)
    private String email;
    private Integer balance;
    private Integer login_failed_attempts;
    private Boolean is_login_disabled;


    public UserEntity() {

    }

    public UserEntity(
            String firstName,
            String lastName,
            String password,
            Integer balance,
            String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.balance = balance;
        this.email = email;
        this.login_failed_attempts = 0;
        this.is_login_disabled = false;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Long getId() {
        return id;
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

    public Integer getBalance() {
        return balance;
    }
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLoginFailedAttempts() {
        return login_failed_attempts;
    }

    public void setLoginFailedAttempts(Integer login_failed_attempts) {
        this.login_failed_attempts = login_failed_attempts;
    }

    public void setIsLoginDisabled(Boolean is_login_disabled) {
        this.is_login_disabled = is_login_disabled;
    }
}
