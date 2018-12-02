package pl.sdacademy.gamechanger.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "app_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class AppUser implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String password;

    private int privilege;

    @OneToOne
    private ContactDetails contactDetails;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Auction> auctions;

    public AppUser(String username, String password, int privilege){
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (this.getPrivilege() > 2) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (this.getPrivilege() > 1) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_RZECZOZNAWCA"));
        }

        if (this.getPrivilege() > 0) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        }

        if (this.getPrivilege() == 0) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_NONE"));
        }

        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
