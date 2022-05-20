package com.danieltalik.fullStackApp.Models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "person_table")
public class PersonUpdate {

    @Id
    @GenericGenerator(name="kaugen",strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    @Column(name="id",unique = true,insertable = false,updatable = false)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="nickname")
    private String nickname;

    @Column(name = "age")
    private int age;

    @Column(name = "birthday")
    private Date birthday;
}
