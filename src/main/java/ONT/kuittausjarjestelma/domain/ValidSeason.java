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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="validSeason")
public class ValidSeason extends RepresentationModel<ValidSeason>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String seasonName;
	private String validFrom;
	private String validTo;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "validSeason")
	private List<Sarja> sarjas;

	
	public ValidSeason() {
		
	}
	
	public ValidSeason(String seasonName) {
		super();
		this.seasonName = seasonName;
		
	}
	
	
	public ValidSeason(String seasonName, String validFrom, String validTo, List<Sarja> sarjas) {
		super();
		this.seasonName = seasonName;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.sarjas = sarjas;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public List<Sarja> getSarjas() {
		return sarjas;
	}

	public void setSarjas(List<Sarja> sarjas) {
		this.sarjas = sarjas;
	}

	@Override
	public String toString() {
		return "ValidSeason [id=" + this.id + ", seasonName=" + this.seasonName + ", validFrom=" + validFrom + ", validTo="
				+ validTo + ", sarjas=" + sarjas + "]";
	}
	
	
	
	
}
