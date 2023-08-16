package AhmetTanrikulu.HRMSBackend.business.concretes;

import AhmetTanrikulu.HRMSBackend.business.abstracts.EmailVerificationService;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.Result;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.EmailVerificationDao;
import AhmetTanrikulu.HRMSBackend.entities.concretes.EmailVerification;
import AhmetTanrikulu.HRMSBackend.entities.concretes.JobAdvert;
import AhmetTanrikulu.HRMSBackend.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailEligiblePreference extends EmailVerificationManager {

    @Autowired
    private  JavaMailSender javaMailSender;



    void sendJobPostNotification(@NotNull List<String> relevantSkills, JobAdvert jobAdvert) {
        if (relevantSkills.size() == 0) throw new NullPointerException("No relevent user exits");
        String[] strings = new String[relevantSkills.size()];
        int i = 0;
        for (String s : relevantSkills) {
            strings[i++] = s;
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(strings);
        simpleMailMessage.setSubject(jobAdvert.getPosition().getPositionName() + "" + jobAdvert.getPositionId());
        simpleMailMessage.setText(jobAdvert.getDescription()+"\n"+jobAdvert.getAdStatusDescription()+"\n"+jobAdvert.getSkills().getSkills() + " " + jobAdvert.getSkills().getAdditionalSkills() );
        try {
            javaMailSender.send(simpleMailMessage);
        }catch (MailException mailException)
        {
            mailException.fillInStackTrace();
        }
    }
}
