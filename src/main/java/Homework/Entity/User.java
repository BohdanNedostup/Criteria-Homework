package Homework.Entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(callSuper = true, exclude = {"country", "city"})
public class User extends BaseEntity{

    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @Column(name = "age", nullable = false)
    private int age;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    private Country country;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
              fetch = FetchType.LAZY)
    @JoinColumn(name = "city")
    private City city;
}
