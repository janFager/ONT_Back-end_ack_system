package ONT.kuittausjarjestelma.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidSeasonRepository extends CrudRepository <ValidSeason, Long>{
	List<ValidSeason>findAll();
}

