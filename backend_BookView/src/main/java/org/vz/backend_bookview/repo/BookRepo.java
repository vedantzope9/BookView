package org.vz.backend_bookview.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vz.backend_bookview.model.Book;

public interface BookRepo extends JpaRepository<Book,Integer> {
}
