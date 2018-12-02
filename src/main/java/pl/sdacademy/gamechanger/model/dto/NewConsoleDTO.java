package pl.sdacademy.gamechanger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewConsoleDTO {

    private String name;

    private Long hardDriveGB;
    private String platform;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate dateOfProduction;
    private Boolean isNew;
    private Boolean isWarranty;




}
