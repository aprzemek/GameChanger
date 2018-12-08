package pl.sdacademy.gamechanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "auction")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private AppUser user;

    @OneToOne
    private Item item;

    private LocalDateTime expirationDate;

    private LocalDateTime startDate;

    private String title;

    private String platform;

    @Column(length = 600)
    private String description;

    private Boolean isAccepted;

    private Boolean isAvailable;

    @OneToMany
    private Set<Auction> tradeOffers;
}
