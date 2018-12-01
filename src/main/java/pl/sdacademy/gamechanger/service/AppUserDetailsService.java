package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.AppUser;
import pl.sdacademy.gamechanger.repository.AppUserRepository;

import java.util.Optional;
@Service

public class AppUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

   @Autowired
   private AppUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> foundUser = userRepository.findAppuserByUsername(username);

        if (foundUser.isPresent()) {
            AppUser appUser = foundUser.get();

            return new User(
                    appUser.getUsername(),
                    appUser.getPassword(),
                    appUser.getAuthorities());

        }

            throw new UsernameNotFoundException("Username not found");
        }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String s) {
        return null;
    }
}

