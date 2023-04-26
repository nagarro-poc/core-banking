package org.bfsi.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long userId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String contactNumber;

    @Column
    private String emailId;

    @Column
    private String address;

    @Column(length = 10, unique = true)
    private String userName;

    /*
     * @Column
     *
     * @DefaultValue(value=CurrentTimestamp) private Date createdAt;
     */
}
