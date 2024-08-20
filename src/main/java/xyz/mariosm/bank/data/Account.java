package xyz.mariosm.bank.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Document
public class Account implements UserDetails {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private AccountTypes type;
    private AccountRoles role;


    public Account() {
    }

    public Account(String username, String password) {
        this(username, password, AccountTypes.INDIVIDUAL);
    }

    public Account(String username, String password, AccountTypes type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.role = AccountRoles.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(ObjectId id) { this.id = id; }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountTypes getType() {
        return type;
    }

    public void setType(AccountTypes type) {
        this.type = type;
    }

    public AccountRoles getRole() {
        return role;
    }

    public void setRole(AccountRoles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(password, account.password) && type == account.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, type);
    }

    @Override
    public String toString() {
        return "Account{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", type=" + type + '}';
    }
}
