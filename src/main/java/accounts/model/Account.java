package accounts.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "Accountid")
    private Long id;

    @NotNull
    @NotEmpty
    @Email
    @Column(name = "Email", unique = true, nullable = false, updatable = false)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6)
    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "Address")
    private String address;

}
