package AhmetTanrikulu.HRMSBackend.entities.concretes;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userId;
	
	@Column(name="email")
	@NotNull
	@NotBlank
	@Email
	private String email;
	
	@Column(name="password")
	@NotNull
	@NotBlank
	private String password;
	
	@Column(name="imageUrl")
	private String imageUrl;

	@OneToOne
	@JoinColumn(name = "preference")
	private Skills preference;
	
}