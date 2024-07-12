package tpo.api.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Optional<Discount> findByCode(String code);

    Optional<Discount> findByCodeIgnoreCase(String code);

}
