package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.Game;
import pl.sdacademy.gamechanger.model.dto.NewGameDTO;
import pl.sdacademy.gamechanger.repository.GameRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Optional<Game> findByID(long id) {
        return gameRepository.findById(id);
    }

    public List<Game> findVideoGameByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }


    public List<Game> findByPlatform(String platform) {
        return gameRepository.findAllByPlatformContaining(platform);
    }



    public Optional<Game> findById(long id){
        return gameRepository.findById(id);
    }

    public List<Game> findNew() {
        return gameRepository.findAllByIsNewIsTrue();
    }

    public List<Game> findUsed() {
        return gameRepository.findAllByIsNewIsFalse();
    }


    public void addGame(Game game) { gameRepository.save(game);
    }

    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }

    public void updateGame(Game videoGame) {
        Optional<Game> optionalGame = gameRepository.findById((videoGame.getId()));

        if (optionalGame.isPresent()) {
            Game updatedVideoGame = optionalGame.get();

            if (videoGame.getTitle() != null) {
                updatedVideoGame.setTitle(videoGame.getTitle());
            }



            if (videoGame.getPlatform() != null) {
                updatedVideoGame.setPlatform(videoGame.getPlatform());
            }



            gameRepository.save(updatedVideoGame);
        }
    }

    public Game addGame(NewGameDTO newGameDTO) {
        Game game = new Game();
        game.setTitle(newGameDTO.getTitle());
        game.setPlatform(newGameDTO.getPlatform());
        game.setIsNew(newGameDTO.getIsNew());

        return gameRepository.save(game);
    }


}
