package dev.yrmg.userwebapi.userwebapi.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.yrmg.userwebapi.userwebapi.domain.entities.Phone;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, Long>{
    Optional<Phone> findByNumber(String number);
}
