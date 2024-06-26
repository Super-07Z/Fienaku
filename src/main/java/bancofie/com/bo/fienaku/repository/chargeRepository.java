package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.charge;
import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface chargeRepository extends JpaRepository<charge, Long> {
    boolean existsByFienaku(fienaku fienaku);

    public boolean existsByFienakuAndUser(fienaku data, user currentUser);
}
