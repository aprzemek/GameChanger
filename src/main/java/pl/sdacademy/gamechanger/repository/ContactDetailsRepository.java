package pl.sdacademy.gamechanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdacademy.gamechanger.model.ContactDetails;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails,Long> {
}
