package pl.sdacademy.gamechanger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewGameDTO {
    private String title;
    private String platform;
    private Boolean isNew;

}
