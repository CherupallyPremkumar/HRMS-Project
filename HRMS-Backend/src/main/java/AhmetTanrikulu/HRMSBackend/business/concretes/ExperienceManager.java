package AhmetTanrikulu.HRMSBackend.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AhmetTanrikulu.HRMSBackend.business.abstracts.ExperienceService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.Result;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessDataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessResult;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.ExperienceDao;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Experience;

@Service
public class ExperienceManager implements ExperienceService{
	

	private ExperienceDao experienceDao;
	
	@Autowired
	public ExperienceManager(ExperienceDao experienceDao) {
		super();
		this.experienceDao = experienceDao;
	}

	
	@Override
	public Result add(Experience experience) {
		if(experience.getQuitDate() == null) {
			experience.setWorkingStatus_b(true);
			experience.setWorkingStatus("Devam ediyor");
		}else {
			experience.setWorkingStatus_b(false);
			experience.setWorkingStatus("Devam etmiyor");
		}
		this.experienceDao.save(experience); 
		return new SuccessResult("Tecrübeniz eklendi");
	}
	
	@Override
	public Result delete(int id) {
		this.experienceDao.deleteById(id);
		return new SuccessResult("Tecrübeniz silindi");
	}

	@Override
	public DataResult<List<Experience>> getAll() {
		return new SuccessDataResult<List<Experience>>(this.experienceDao.findAll(),"Tecrübeler getirildi");
	}


	@Override
	public DataResult<List<Experience>> getAllByUserIdOrderByQuitDate(int userId) {
		return new SuccessDataResult<List<Experience>>(this.experienceDao.getAllByUserIdOrderByQuitDateAsc(userId));
	}





	

}
