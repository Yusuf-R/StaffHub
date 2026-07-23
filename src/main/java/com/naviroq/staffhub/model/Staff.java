package com.naviroq.staffhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "staff", schema = "\"staffHub\"") // tell it to use staffHub instead of converting it to staff_hub
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false) // name omitted, will become 'id'
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Override
    public String toString() {
        return String.format("Staff[id: %s -- FirsName: %s -- LastName: %s -- Email: %s]", id, firstName, lastName, email);
    }
}