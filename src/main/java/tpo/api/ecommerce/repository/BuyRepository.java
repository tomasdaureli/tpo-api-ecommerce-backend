package tpo.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tpo.api.ecommerce.entity.Buy;

public interface BuyRepository extends JpaRepository<Buy, Long> {

}
