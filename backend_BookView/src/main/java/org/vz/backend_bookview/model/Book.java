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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    private String title;
    private String author;
    private String description;
    private double averageRating;
    private Date publishedDate;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] imageData;
}
