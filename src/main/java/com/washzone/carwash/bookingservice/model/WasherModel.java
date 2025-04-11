package com.washzone.carwash.bookingservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "washers")
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WasherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private boolean isAvailable;

    @OneToOne
    @JoinColumn(name = "user")
    @JsonBackReference
    private User user;

    public WasherModel(String name, String email, boolean isAvailable) {
        this.name = name;
        this.email = email;
        this.isAvailable = isAvailable;

    }
}
