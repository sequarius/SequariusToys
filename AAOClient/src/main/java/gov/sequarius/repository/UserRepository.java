package gov.sequarius.repository;

import gov.sequarius.domain.entry.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Sequarius on 2016/4/14.
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    User findByUsername(String username);
}
