package pl.sdacademy.gamechanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.sdacademy.gamechanger.model.interfaces.AuctionFields;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "console")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Console implements AuctionFields {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String platform;
    private Long hardDriveGb;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate dateOfProduction;
    private Boolean isNew;
    private Boolean isWarranty;

    @Override
    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap = new HashMap<>();

        fieldMap.put("Dysk twardy", hardDriveGb+ " "+"GB");
        fieldMap.put("Data produkcji",dateOfProduction);
        fieldMap.put("Czy jest nowy?",isNew);
        fieldMap.put("Czy ma gwarancje?",isWarranty);




        return fieldMap;
    }


}
