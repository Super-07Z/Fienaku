package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.payment;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface paymentRepository extends JpaRepository<payment, Long> {

}
