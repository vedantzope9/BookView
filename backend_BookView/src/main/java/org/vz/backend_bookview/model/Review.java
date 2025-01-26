package org.vz.backend_bookview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId" )
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book bookId;

    private double rating;
    private String comment;
    private Date createdDate;
}
