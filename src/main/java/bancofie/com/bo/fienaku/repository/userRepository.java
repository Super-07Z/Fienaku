package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.user;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Long> {

    List<user> findByFienakuId(Long fienakuId);

    user findByUsername(String username);
    
}
