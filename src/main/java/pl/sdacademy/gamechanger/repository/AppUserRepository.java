package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    List<AppUser> findAllByUsernameContaining(String username);

    Optional<AppUser> findAppUserByUsername(String username);

    Optional<AppUser> findByPassword(String code);
}
