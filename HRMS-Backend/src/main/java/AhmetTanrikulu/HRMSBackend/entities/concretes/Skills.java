package AhmetTanrikulu.HRMSBackend.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Skills {
    @Id
    @Column(name = "skills_id")
    private int skillsID;
    @NotNull
    @Column(name = "skills")
    private String skills;
    @NotNull
    @Column(name = "additionalSkills")
    private String additionalSkills;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
}
