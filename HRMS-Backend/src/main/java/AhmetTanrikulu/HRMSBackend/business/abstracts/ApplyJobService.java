package AhmetTanrikulu.HRMSBackend.business.abstracts;

import AhmetTanrikulu.HRMSBackend.entities.concretes.Resume;

public interface ApplyJobService {
    void upload(Resume resume);

    Resume getResume(int user_id);
}
