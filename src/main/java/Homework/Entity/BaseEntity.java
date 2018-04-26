package Homework.Entity;

import lombok.*;
import javax.persistence.*;

@MappedSuperclass
@Getter @Setter
@ToString
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
