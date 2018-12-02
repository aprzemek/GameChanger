package pl.sdacademy.gamechanger.model;

import lombok.*;
import pl.sdacademy.gamechanger.model.interfaces.AuctionFields;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "game")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Game implements AuctionFields {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String platform;
    private Boolean isNew;

    @Override
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();

        fieldMap.put("Gra", title);
        fieldMap.put("Platforma", platform);
        fieldMap.put("Czy jest nowa?", isNew);

        return fieldMap;


    }
}