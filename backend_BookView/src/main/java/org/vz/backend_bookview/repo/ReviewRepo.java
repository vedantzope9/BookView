package org.vz.backend_bookview.repo;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vz.backend_bookview.model.Review;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findByBookId(int bookId);
    
    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.bookId = :bookId")
    Double getSumOfRatingsByBookId(@Param("bookId") int bookId);
}
