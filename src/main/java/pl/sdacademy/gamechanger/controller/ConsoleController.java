package pl.sdacademy.gamechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sdacademy.gamechanger.model.Console;
import pl.sdacademy.gamechanger.model.dto.NewConsoleDTO;
import pl.sdacademy.gamechanger.service.ConsoleService;
import pl.sdacademy.gamechanger.service.ItemService;

@Controller
@RequestMapping(path = "/console")
public class ConsoleController {

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private ItemService itemService;

    @GetMapping(path = "/add")
    public String getAddAuction(@RequestParam(name = "id")Long itemId, Model model){
    model.addAttribute("console",new NewConsoleDTO());
    model.addAttribute("item_id",itemId);

    return "addConsole";
    }
    @PostMapping(path = "/add")
    public String addAuction(NewConsoleDTO consoleDTO,
                             @RequestParam(name = "itemId")Long itemId){
        System.out.println(consoleDTO.getDateOfProduction());
        Console console = consoleService.addConsole(consoleDTO);
        itemService.updateItemId(itemId, console.getId());

        return "redirect:/user/panel";

    }


}
