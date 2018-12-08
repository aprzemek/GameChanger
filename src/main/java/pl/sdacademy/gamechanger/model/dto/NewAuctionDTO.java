package pl.sdacademy.gamechanger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAuctionDTO {

    private String title;

    private String description;

    private Integer duration;

    private String platform;

    private long estimatedPrice;

    private long categoryId;

    private String username;
}
