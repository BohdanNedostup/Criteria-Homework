package Homework.Entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(callSuper = true, exclude = "cities")
public class Country extends BaseEntity{

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();
}
