package AhmetTanrikulu.HRMSBackend.business.concretes;

import AhmetTanrikulu.HRMSBackend.business.abstracts.ApplyJobService;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.ApplyJobDao;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplyJob implements ApplyJobService {
    @Autowired
    ApplyJobDao applyJobDao;
    @Override
    public void upload(Resume resume) {
      applyJobDao.save(resume);
    }

    @Override
    public Resume getResume(int user_id) {
        Optional<Resume> resume=applyJobDao.findById(user_id);
        if(resume.isPresent())
        {
            return resume.get();
        }else {
            throw new NullPointerException();
        }
    }
}
