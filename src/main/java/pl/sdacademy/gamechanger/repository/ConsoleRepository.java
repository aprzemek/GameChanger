package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.Console;

import java.time.LocalDate;
import java.util.List;

public interface ConsoleRepository extends JpaRepository<Console, Long> {

    List<Console> findAllByNameContaining(String name);

    List<Console> findAllByHardDriveGbIsBetween(Long min, Long max);

    List<Console> findAllByIsNewIsFalse();

    List<Console> findAllByIsNewIsTrue();

    List<Console> findAllByIsWarrantyIsTrue();

    List<Console> findAllByDateOfProductionIsBetween(LocalDate min, LocalDate max);






}
