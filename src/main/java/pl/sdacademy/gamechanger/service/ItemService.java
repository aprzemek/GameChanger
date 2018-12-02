package pl.sdacademy.gamechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdacademy.gamechanger.model.Item;
import pl.sdacademy.gamechanger.repository.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public void addItem(Item item) {
        itemRepository.save(item);
    }

    public void updateItemId(long id, long itemId) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        Item item = itemOptional.get();
        item.setItemId(itemId);

        itemRepository.save(item);
    }
}
