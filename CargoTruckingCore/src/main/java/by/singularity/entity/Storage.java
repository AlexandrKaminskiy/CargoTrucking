package by.singularity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "storage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max = 20,min = 1)
    private String name;

    @Length(max = 20,min = 1)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Client.class,optional = false)
    private Long userId;

}