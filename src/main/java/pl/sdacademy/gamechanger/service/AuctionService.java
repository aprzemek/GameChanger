package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.AppUser;
import pl.sdacademy.gamechanger.model.Auction;
import pl.sdacademy.gamechanger.model.Item;
import pl.sdacademy.gamechanger.model.dto.NewAuctionDTO;
import pl.sdacademy.gamechanger.repository.AuctionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AppUserService appUserService;

    public Optional<Auction> addAuction(NewAuctionDTO newAuctionDTO) {
        Auction auction = new Auction();
        Item item = new Item();

        AppUser user = appUserService.findByUsername(newAuctionDTO.getUsername());

        item.setCategory(categoryService.findCategoryById(newAuctionDTO.getCategoryId()));
        item.setEstimatedPrice(newAuctionDTO.getEstimatedPrice());
        itemService.addItem(item);

        auction.setIsAccepted(false);
        auction.setIsAvailable(true);
        auction.setDescription(newAuctionDTO.getDescription());
        auction.setTitle(newAuctionDTO.getTitle());
        auction.setExpirationDate(LocalDateTime.now().plusDays(newAuctionDTO.getDuration()));
        auction.setStartDate(LocalDateTime.now());
        auction.setItem(item);
        auction.setUser(user);

        auction = auctionRepository.save(auction);

        appUserService.addAuction(user, auction);

        return Optional.of(auction);
    }

    public void saveAuction(Auction auction){
        auctionRepository.save(auction);
    }

    public Optional<Auction> findById(long id) {
        return auctionRepository.findById(id);
    }

    public List<Auction> findByUserId(long id) {
        return auctionRepository.findAllByUser_Id(id);
    }

    public List<Auction> findByCategory(String category) {
        return auctionRepository.findAllByItem_Category_TableName(category);
    }

    public void deleteAuction(long id) {
        auctionRepository.deleteById(id);
    }


    public Auction findByProductAndCategory(Long laptopId) {
        return null;
    }

    public List<Auction> findByTitleContaining(String title) {
        return auctionRepository.findAllByTitleContaining(title);
    }

    public List<Auction> findAllNotAccepted() {
        return auctionRepository.findAllByIsAcceptedIsFalse();
    }

    public boolean acceptAuction(long id) {
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            auction.setIsAccepted(true);
            auctionRepository.save(auction);
            return true;
        }

        return false;
    }

    public boolean lockAuction(long id) {
        Optional<Auction> auctionOptional = auctionRepository.findById(id);

        if (auctionOptional.isPresent()) {
            Auction auction = auctionOptional.get();
            auction.setIsAccepted(false);
            auctionRepository.save(auction);
            return true;
        }

        return false;
    }
}
