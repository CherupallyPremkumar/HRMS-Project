package AhmetTanrikulu.HRMSBackend.business.abstracts;

import java.util.List;

import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.Result;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Image;
import AhmetTanrikulu.HRMSBackend.exception.UserNotExitsException;

public interface ImageService {

	DataResult<List<Image>> getAll();
	Result add(Image image) throws UserNotExitsException;
	
	DataResult<Image> getByUserId(int userId);
	
}