package AhmetTanrikulu.HRMSBackend.entities.concretes;



import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;


@Getter
@Setter
@Entity
@Table(name="job_adverts")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","favorites"})
public class JobAdvert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="job_advert_id")
	private int jobAdvertId;

	@Column(name="min_salary")
	private float minSalary;

	@Column(name="max_salary")
	private float maxSalary;

	@Column(name="quantity")
	@NotNull
	private int quantity;

	@Column(name="advert_date")
	private LocalDate advertDate;

	@Column(name="due_date")
	@NotNull
	private LocalDate dueDate;

	@Column(name="description")
	@NotNull
	@NotBlank
	private String description;

	@Column(name="activity_status")
	private boolean activityStatus;

	@Column(name="ad_status_description")
	private String adStatusDescription;

	@Column(name="position_id")
	@NotNull
	private int positionId;

	@Column(name="city_id")
	@NotNull
	private int cityId;

	@Column(name="user_id")
	@NotNull
	private int userId;

	@Column(name="place_type_id")
	@NotNull
	private int placeTypeId;

	@Column(name="time_type_id")
	@NotNull
	private int timeTypeId;

	@ManyToOne
	@JoinColumn(name = "skills_id")
	@NotNull
	private Skills skills;

	@ManyToOne()
	@JoinColumn(name = "position_id", insertable = false, updatable = false)
	@NotNull
	private Position position;

	@ManyToOne()
	@JoinColumn(name = "city_id", insertable = false, updatable = false)
	private City city;

	@ManyToOne()
	@JoinColumn(name = "place_type_id", insertable = false, updatable = false)
	private JobTypePlace jobTypePlace;

	@ManyToOne()
	@JoinColumn(name = "time_type_id", insertable = false, updatable = false)
	private JobTypeTime jobTypeTime;

	@ManyToOne()
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private Employer employer;

	@OneToMany(mappedBy = "jobAdvert")
	private List<Favorite> favorites;
}
