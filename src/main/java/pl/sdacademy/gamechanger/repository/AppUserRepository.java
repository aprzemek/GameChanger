package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findAppuserByUsername(String username);
}
