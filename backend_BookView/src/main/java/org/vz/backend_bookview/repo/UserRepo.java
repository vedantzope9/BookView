package org.vz.backend_bookview.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vz.backend_bookview.model.Users;

public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);
}
