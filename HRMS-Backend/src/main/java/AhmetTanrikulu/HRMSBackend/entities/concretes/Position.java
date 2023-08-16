package AhmetTanrikulu.HRMSBackend.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Getter
@Setter
@Entity
@Table(name="positions")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","jobAdverts"})
public class Position {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="position_id")
	private int positionId;
	
	@Column(name="position_name")
	@NotNull
	@NotBlank
	private String positionName;
	
	@OneToMany(mappedBy = "position")
	private List<JobAdvert> jobAdverts;
	
	
	}