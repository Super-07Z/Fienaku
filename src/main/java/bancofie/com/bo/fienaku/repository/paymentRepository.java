package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface paymentRepository extends JpaRepository<payment, Long> {

}
