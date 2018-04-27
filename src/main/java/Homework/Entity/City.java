package Homework.Entity;


import lombok.*;
import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(callSuper = true, exclude = "country")
public class City extends BaseEntity{

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    private Country country;
}
