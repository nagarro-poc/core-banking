package org.bfsi.notification.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String firstName;

    private String lastName;

    private String contactNumber;

    private String emailId;

    private String address;

    private String userName;
}
