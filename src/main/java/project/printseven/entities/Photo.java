package project.printseven.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Table(name = "photos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(generator = "increment",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "increment",allocationSize = 1)
    private Long id;
    private ZonedDateTime createdAd;
    private String imageUrl;
    private boolean success;
    @ManyToOne
    private User user;
    @PrePersist
    public void prePersist() {
        createdAd = ZonedDateTime.now();
    }
}
