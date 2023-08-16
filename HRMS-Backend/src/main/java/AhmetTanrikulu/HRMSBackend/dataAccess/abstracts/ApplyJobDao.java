package AhmetTanrikulu.HRMSBackend.dataAccess.abstracts;

import AhmetTanrikulu.HRMSBackend.entities.concretes.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyJobDao extends JpaRepository<Resume,Integer> {
}
