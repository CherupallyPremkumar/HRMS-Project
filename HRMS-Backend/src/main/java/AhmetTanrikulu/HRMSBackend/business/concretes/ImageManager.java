package AhmetTanrikulu.HRMSBackend.business.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import AhmetTanrikulu.HRMSBackend.exception.UserNotExitsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import AhmetTanrikulu.HRMSBackend.business.abstracts.ImageService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.Result;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessDataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessResult;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.ImageDao;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.UserDao;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Image;
import AhmetTanrikulu.HRMSBackend.entities.concretes.User;



@Service
@Transactional
public class ImageManager implements ImageService {

	ImageDao imageDao;
	UserDao userDao; 
	
	@Autowired
	public ImageManager(ImageDao imageDao, UserDao userDao) {
		super();
		this.imageDao = imageDao;
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<Image>> getAll() {
		
	return new SuccessDataResult<List<Image>>(this.imageDao.findAll(),"Başarıyla listelendi");
	}

	@Override
	public Result add(Image image) throws UserNotExitsException {
		LocalDate now = LocalDate.now();
		image.setDateOfCreation(now);
		this.imageDao.save(image);
		User user = this.userDao.getByUserId(image.getUserId());
		if(user==null)
		{
			throw new UserNotExitsException("user doest exist :"+image.getUserId());
		}
		user.setImageUrl(image.getImageUrl());
		return new SuccessResult("Başarıyla eklendi");
		
	}

	@Override
	public DataResult<Image> getByUserId(int userId) {
		return new SuccessDataResult<Image>(this.imageDao.getByUserId(userId));
	}

}
