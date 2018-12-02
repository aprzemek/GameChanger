package pl.sdacademy.gamechanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long itemId;

    @ManyToOne
    private Category category;

    private long estimatedPrice;




}
