package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.Category;
import pl.sdacademy.gamechanger.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCategoryAndItemId(Category category, Long id);

}
