package bancofie.com.bo.fienaku.repository;

import java.util.List;

import bancofie.com.bo.fienaku.model.fienaku;
import bancofie.com.bo.fienaku.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fienakuRepository extends JpaRepository<fienaku, Long> {
    fienaku findByUser(user user);
}
