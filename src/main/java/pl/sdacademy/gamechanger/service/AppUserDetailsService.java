package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.AppUser;
import pl.sdacademy.gamechanger.repository.AppUserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> foundUser = userRepository.findAppUserByUsername(username);

        if (foundUser.isPresent()) {
            AppUser appUser = foundUser.get();

            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .authorities(new HashSet<>(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))).build();
        }

        throw new UsernameNotFoundException("Username not found");
    }

}

