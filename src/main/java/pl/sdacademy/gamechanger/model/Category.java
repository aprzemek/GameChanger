package pl.sdacademy.gamechanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String tableName;

    public Category(String name, String tableName) {
        this.name = name;
        this.tableName = tableName;
    }


}
