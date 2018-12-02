package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.Auction;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

List<Auction> findAllByItem_Category_TableName(String categoryName);

List<Auction> findAllByUser_Id(long userId);

List <Auction> findAllByTitleContaining(String title);

List<Auction> findAllByIsAcceptedIsFalse();



}
