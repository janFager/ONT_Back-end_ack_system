package ONT.kuittausjarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface SarjaRepository extends CrudRepository <Sarja, Long>{
	List<Sarja>findAll();
	
	
}
