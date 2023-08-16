package AhmetTanrikulu.HRMSBackend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@NoArgsConstructor
public class UserNotExitsException extends Exception{
    public UserNotExitsException(String str)
    {
        super(str);
    }
    @ExceptionHandler(UserNotExitsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
     public Map<String, String> handleException(UserNotExitsException exception)
     {
         Map<String,String> message=new HashMap<>();
         message.put("Error Message UserNotFoundException",exception.getMessage());
    return message;
     }

     @ExceptionHandler(MultipartException.class)
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     Map<String,String>handleException(MultipartException nullPointerException)
     {
       Map<String,String> message= new HashMap<>();
       message.put("Multipart File:Error",nullPointerException.getMessage());
       return message;
     }
}
