package org.vz.backend_bookview.repo;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vz.backend_bookview.model.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
}
