package AhmetTanrikulu.HRMSBackend.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import AhmetTanrikulu.HRMSBackend.business.abstracts.EmployeeService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.ErrorDataResult;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Employee;
import AhmetTanrikulu.HRMSBackend.entities.dtos.CurriculumVitaeDto;

@RestController
@RequestMapping("/api/employees/")
@CrossOrigin
public class EmployeesController {
	
	private EmployeeService employeeService;

	@Autowired
	public EmployeesController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping("getall")
	public DataResult<List<Employee>> getAll(){
		return this.employeeService.getAll();
	}
	
	@GetMapping("getcvbyuserid")
	public DataResult<CurriculumVitaeDto> getCvByUserId(@RequestParam int userId){
		return this.employeeService.getCurriculumVitaeByUserId(userId);
	}
	
	@GetMapping("getbyuserid")
	public DataResult<Employee> getByUserId(@RequestParam int userId){
		return this.employeeService.getByUserId(userId);
	}
	
	@PostMapping("add")
	public ResponseEntity<?> add(@Valid @RequestBody Employee employee) {
		System.out.println(employee.toString());
		return ResponseEntity.ok(this.employeeService.add(employee));
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
