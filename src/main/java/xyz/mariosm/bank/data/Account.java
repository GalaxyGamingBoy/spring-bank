package xyz.mariosm.bank.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.mariosm.bank.http.AccountDetailsRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Setter
@Document
@NoArgsConstructor
public class Account implements UserDetails {
    @Id
    @Getter
    @JsonIgnore
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String username;

    private String password;

    @Getter
    @NonNull
    private AccountTypes type;

    @JsonIgnore
    @Getter
    private AccountRoles role;

    public Account(AccountDetailsRequest details) {
        this(details.getUsername(), details.getPassword(), details.getType());
    }

    @JsonCreator
    public Account(String username, String password, AccountTypes type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.role = AccountRoles.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Add the current role
        authorities.add(new SimpleGrantedAuthority("ROLE_" + getRole().name()));

        // If super admin also add admin role
        if (getRole() == AccountRoles.SUPER_ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
