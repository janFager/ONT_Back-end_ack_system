package ONT.kuittausjarjestelma.domain;


import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="validWeekDay")
public class ValidWeekDay extends RepresentationModel<ValidWeekDay>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String validWeekDayName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "validWeekDay")
	private List<Sarja> sarjas;

	public ValidWeekDay(String validWeekDayName) {
		super();
		this.validWeekDayName = validWeekDayName;
	}

	public ValidWeekDay() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Sarja> getSarjas() {
		return sarjas;
	}

	public void setSarjas(List<Sarja> sarjas) {
		this.sarjas = sarjas;
	}
	

	public String getValidWeekDayName() {
		return validWeekDayName;
	}

	public void setValidWeekDayName(String validWeekDayName) {
		this.validWeekDayName = validWeekDayName;
	}

	@Override
	public String toString() {
		return "ValidWeekDay [id=" + id + ", validWeekDayName=" + validWeekDayName + ", sarjas=" + sarjas + "]";
	}

	
	
}
