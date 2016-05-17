package gov.sequarius.repository;

import gov.sequarius.domain.entry.AAOAcount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Sequarius on 2016/4/14.
 */
@Repository
public interface AAOAcountRepository extends CrudRepository<AAOAcount,Long>{
}
