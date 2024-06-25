package bancofie.com.bo.fienaku.repository;

import bancofie.com.bo.fienaku.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, Long> {

    user findByMail(String mail);
}
