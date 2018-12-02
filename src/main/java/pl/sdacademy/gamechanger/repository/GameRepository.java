package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.Game;

import java.util.List;

public interface GameRepository extends JpaRepository<Game,Long> {

    List<Game> findAllByTitleContaining (String title);

    List<Game> findAllByPlatformContaining(String platform);

    List<Game> findAllByIsNewIsFalse();

    List<Game> findAllByIsNewIsTrue();


}
