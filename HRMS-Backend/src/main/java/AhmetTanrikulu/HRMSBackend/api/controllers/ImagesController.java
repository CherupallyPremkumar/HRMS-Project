package AhmetTanrikulu.HRMSBackend.api.controllers;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import AhmetTanrikulu.HRMSBackend.core.utilities.results.ErrorDataResult;
import AhmetTanrikulu.HRMSBackend.exception.UserNotExitsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import AhmetTanrikulu.HRMSBackend.business.abstracts.ImageService;
import AhmetTanrikulu.HRMSBackend.core.Service.CloudinaryService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Image;


@RestController
@RequestMapping("/cloudinary")
@CrossOrigin
public class ImagesController {

	
	CloudinaryService cloudinaryService;	
	ImageService imageService;
	
	@Autowired
	public ImagesController(CloudinaryService cloudinaryService, ImageService imageService) {
		super();
		this.cloudinaryService = cloudinaryService;
		this.imageService = imageService;
	}


	@GetMapping("getAll")
	public ResponseEntity<List<Image>> getAll(){
		
		List<Image> list  = this.imageService.getAll().getData();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("getallbyuserid")
	public DataResult<Image> getByUserId (int userId){


		return this.imageService.getByUserId(userId);
	}
	
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload ( @NonNull Image image, @ModelAttribute @NonNull MultipartFile multipartFile) throws IOException, UserNotExitsException {
		System.out.println(image.toString());
		BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
		if(bufferedImage == null) {
			return new ResponseEntity<>("image is empty",HttpStatus.BAD_REQUEST);
		}
		Map uploadResult= cloudinaryService.upload(multipartFile);
		
		image.setName((String)uploadResult.get("original_filename"));
		image.setImageUrl((String)uploadResult.get("url"));
		image.setImageId((String)uploadResult.get("public_id"));
		System.out.println(image.toString());
	
		
		this.imageService.add(image);
		return new ResponseEntity<>("Image uploaded",HttpStatus.OK);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
		Map<String,String> validationErrors = new HashMap<String, String>();
		for(FieldError fieldError :  exceptions.getBindingResult().getFieldErrors() ) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors,"Doğrulama hataları");
		return errors;
	}
	
}