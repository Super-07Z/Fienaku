package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.charge;
import bancofie.com.bo.fienaku.model.fienaku;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface chargeRepository extends JpaRepository<charge, Long> {
    boolean existsByFienaku(fienaku fienaku);
}
