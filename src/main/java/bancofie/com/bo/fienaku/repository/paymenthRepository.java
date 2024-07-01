package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.paymenth;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface paymenthRepository extends JpaRepository<paymenth, Long> {

}
