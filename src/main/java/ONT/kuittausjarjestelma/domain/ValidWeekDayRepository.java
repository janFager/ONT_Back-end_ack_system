package ONT.kuittausjarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidWeekDayRepository extends CrudRepository<ValidWeekDay, Long>{
	
	List<ValidWeekDay> findAll();
}
