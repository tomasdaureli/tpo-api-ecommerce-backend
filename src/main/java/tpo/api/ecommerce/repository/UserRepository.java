package tpo.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
