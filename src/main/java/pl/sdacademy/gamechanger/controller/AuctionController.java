package pl.sdacademy.gamechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sdacademy.gamechanger.model.*;
import pl.sdacademy.gamechanger.model.dto.NewAuctionDTO;
import pl.sdacademy.gamechanger.service.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class AuctionController {
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private ConsoleService consoleService;


    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping(path = "/list/category/{category}")
    public String getAuctionListByCategory(Model model, @PathVariable(name = "category") String category) {
        List<Auction> byCategory = auctionService.findByCategory(category);

        model.addAttribute("auctionList", byCategory);


        return "/auctionList";
    }

    @GetMapping(path = "/list/title")
    public String getAuctionListByTitle(Model model, @RequestParam(name = "title") String title) {
        List<Auction> byTitle = auctionService.findByTitleContaining(title);

        model.addAttribute("auctionList", byTitle);

        return "/auctionList";
    }

    @GetMapping(path = "/get/{id}")
    public String getAuction(Model model, @PathVariable(name = "id") long id) {

        Optional<Auction> auctionOptional = auctionService.findById(id);

        if (!auctionOptional.isPresent()) {
            return "/home";
        }

        Auction auction = auctionOptional.get();

        model.addAttribute("auction", auction);

        String tableName = auction.getItem().getCategory().getTableName();
        Long itemId = auction.getItem().getItemId();

     if ("console".equals(tableName)) {
            Optional<Console> console = consoleService.findById(itemId);
            model.addAttribute("item", console.get());
            model.addAttribute("fieldMap", console.get().getFieldMap());
        } else if ("game".equals(tableName)) {
            Optional<Game> game = gameService.findById(itemId);
            model.addAttribute("item", game.get());
            model.addAttribute("fieldMap", game.get().getFieldMap());
        }

        return "/auction";
    }

    @GetMapping(path = "/add")
    public String getAddAuction(Model model) {
        model.addAttribute("auction", new NewAuctionDTO());


        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "addAuction";
    }


    @PostMapping(path = "/add")
    public String addAuction(NewAuctionDTO newAuction, Principal principal) {
        newAuction.setUsername(principal.getName());

        Optional<Auction> auction = auctionService.addAuction(newAuction);

        return "redirect:/" + categoryService.findCategoryById(newAuction.getCategoryId()).getTableName() + "/add?id=" + auction.get().getItem().getId();
    }

    @GetMapping(path = "/accept/{id}")
    public String acceptAuction(@PathVariable(name = "id") long id, Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName());
        if (user.getPrivilege() > 1) {
            auctionService.acceptAuction(id);
            return "redirect:/user/panel";
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/lock/{id}")
    public String lockAuction(@PathVariable(name = "id") long id, Principal principal) {
        AppUser user = appUserService.findByUsername(principal.getName());
        if (user.getPrivilege() > 1) {
            auctionService.lockAuction(id);
            return "redirect:/user/panel";
        }
        return "redirect:/home";
    }

    @GetMapping(path = "/offers/{id}")
    public String getAuctionOffers(Model model, @PathVariable(name = "id") long id) {

        Optional<Auction> auctionOptional = auctionService.findById(id);

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            Set<Auction> tradeOffers = auction.getTradeOffers();

            model.addAttribute("auctionList", tradeOffers);
            return "/auctionList";
        }

        return "redirect:/user/panel";
    }


    @GetMapping(path = "/makeOffer/{offerId}/{myId}")
    public String makeOffer(Model model, @PathVariable(name = "offerId") long offerId, @PathVariable(name = "myId") long myId) {

        Optional<Auction> auctionOptional = auctionService.findById(offerId);

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            Set<Auction> tradeOffers = auction.getTradeOffers();
            tradeOffers.add(auctionService.findById(myId).get());
            auctionService.saveAuction(auction);
        }

        return "redirect:/user/panel";
    }

    @GetMapping(path = "/makeOffer/{offerId}")
    public String getOfferList(Model model, @PathVariable(name = "offerId") long offerId, Principal principal) {
        Optional<AppUser> user = appUserService.findOptionalByUsername(principal.getName());

        model.addAttribute("offerId", offerId);
        user.ifPresent(appUser -> {
            model.addAttribute("auctions", appUser.getAuctions());
        });

        return "/offerList";
    }
}
