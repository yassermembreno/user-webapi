package dev.yrmg.userwebapi.userwebapi.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.yrmg.userwebapi.userwebapi.domain.entities.Phone;

public interface IPhoneRepository extends JpaRepository<Phone, Long>{
    Optional<Phone> findByNumber(String number);
}
