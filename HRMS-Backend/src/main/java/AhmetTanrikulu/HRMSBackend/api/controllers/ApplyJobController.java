package AhmetTanrikulu.HRMSBackend.api.controllers;

import AhmetTanrikulu.HRMSBackend.business.abstracts.ApplyJobService;
import AhmetTanrikulu.HRMSBackend.core.Service.CloudinaryService;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Resume;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/apply")
public class ApplyJobController {

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ApplyJobService applyJobService;
    @PostMapping("/uploadResume")
    void upload(@ModelAttribute MultipartFile multipartFile, Resume resume) throws Exception {
        if(!multipartFile.isEmpty()) {
            try {
                resume.setResumeName(multipartFile.getOriginalFilename());
                resume.setResumebytes(cloudinaryService.convert(multipartFile));
                resume.setSize(multipartFile.getSize());
                applyJobService.upload(resume);

            }catch (MultipartException multipartException)
            {
                multipartException.fillInStackTrace();
            }
        }else {
            throw new MultipartException("multipartFile shouldnt be empty");
        }
    }
    @PostMapping("/getResumebyid")
    File getResume(@RequestParam("user_id") int user_id)
    {
       return applyJobService.getResume(user_id).getResumebytes();
    }
}
