
package AhmetTanrikulu.HRMSBackend.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import AhmetTanrikulu.HRMSBackend.business.abstracts.SystemEmployeeService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.ErrorDataResult;
import AhmetTanrikulu.HRMSBackend.entities.concretes.SystemEmployee;

@RestController
@RequestMapping("/api/systememployees/")
@CrossOrigin
public class SystemEmployeesController {
	
	private SystemEmployeeService systemEmployeeService;

	@Autowired
	public SystemEmployeesController(SystemEmployeeService systemEmployeeService) {
		super();
		this.systemEmployeeService = systemEmployeeService;
	}
	
	@GetMapping("getall")
	public DataResult<List<SystemEmployee>> getAll(){
		return this.systemEmployeeService.getAll();
	}
	
	@GetMapping("getByUserId")
	public DataResult<SystemEmployee> getByUserId(@RequestParam int userId){
		return this.systemEmployeeService.getByUserId(userId);
	}
	
	@GetMapping("getByEmail")
	public DataResult<SystemEmployee> getByEmail(@RequestParam String email){
		return this.systemEmployeeService.getByEmail(email);
	}
	
	@PostMapping(value="add")
	public ResponseEntity<?> add(@Valid @RequestBody SystemEmployee systemEmployee) {
		return ResponseEntity.ok(this.systemEmployeeService.add(systemEmployee));
	}
	
	@PostMapping(value="update")
	public ResponseEntity<?> update(@Valid @RequestBody SystemEmployee systemEmployee) {
		return ResponseEntity.ok(this.systemEmployeeService.update(systemEmployee));
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

