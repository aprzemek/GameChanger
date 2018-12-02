package pl.sdacademy.gamechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sdacademy.gamechanger.model.Game;
import pl.sdacademy.gamechanger.model.dto.NewGameDTO;
import pl.sdacademy.gamechanger.service.GameService;
import pl.sdacademy.gamechanger.service.ItemService;

@Controller
@RequestMapping(path = "/game/")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ItemService itemService;

    @GetMapping(path = "/add")
    public String getAddAuction(
            @RequestParam(name = "id")Long itemId, Model model){

        model.addAttribute("game",new NewGameDTO());
        model.addAttribute("item_id", itemId);

        return "addGame";
    }

    @PostMapping(path = "/add")
    public String addAuction(NewGameDTO newGameDTO,
                             @RequestParam(name ="itemId" )Long itemId){

        Game game = gameService.addGame(newGameDTO);
        itemService.updateItemId(itemId,game.getId());

        return "redirect:/user/panel";

    }





}
