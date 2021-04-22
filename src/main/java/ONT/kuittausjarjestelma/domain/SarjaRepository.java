package ONT.kuittausjarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SarjaRepository extends CrudRepository <Sarja, Long>{
	List<Sarja>findAll();
	
	
}
