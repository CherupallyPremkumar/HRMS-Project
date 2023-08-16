package AhmetTanrikulu.HRMSBackend.business.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import AhmetTanrikulu.HRMSBackend.business.abstracts.UserService;
import AhmetTanrikulu.HRMSBackend.entities.concretes.Skills;
import AhmetTanrikulu.HRMSBackend.entities.concretes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import AhmetTanrikulu.HRMSBackend.business.abstracts.JobAdvertService;
import AhmetTanrikulu.HRMSBackend.core.business.BusinessRules;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.DataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.ErrorResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.Result;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessDataResult;
import AhmetTanrikulu.HRMSBackend.core.utilities.results.SuccessResult;
import AhmetTanrikulu.HRMSBackend.dataAccess.abstracts.JobAdvertDao;
import AhmetTanrikulu.HRMSBackend.entities.concretes.JobAdvert;

@Service
public class JobAdvertManager implements JobAdvertService {

    @Autowired
    private JobAdvertDao jobAdvertDao;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailEligiblePreference emailEligiblePreference;



    @Override
    public Result add(JobAdvert jobAdvert) {
        var result = BusinessRules.run(CheckIfSalary(jobAdvert));
        if (result != null) {
            return result;
        }
        LocalDate now = LocalDate.now();
        jobAdvert.setAdvertDate(now);
        jobAdvert.setActivityStatus(false); //Sistem çalışanı onayı gerekli!
        jobAdvert.setAdStatusDescription("AWAITING APPROVAL");
        this.jobAdvertDao.save(jobAdvert);
        return new SuccessResult("After approval, it will be published.");
    }

    @Override
    public Result closeAdvert(int jobAdvertId) {
        var jobAdvert = this.jobAdvertDao.getByJobAdvertId(jobAdvertId);
        if (jobAdvert.isActivityStatus()) {
            jobAdvert.setAdStatusDescription("İŞVEREN TARAFINDAN KALDIRILDI");
            jobAdvert.setActivityStatus(false);
            this.jobAdvertDao.save(jobAdvert);
            return new SuccessResult("İlan yayından kaldırıldı");
        } else if (!jobAdvert.isActivityStatus()) {
            return new ErrorResult("İlan zaten yayında değil");
        }
        return new ErrorResult("Hata");
    }

    @Override
    public Result closeAdvertAdmin(int jobAdvertId) {
        var jobAdvert = this.jobAdvertDao.getByJobAdvertId(jobAdvertId);
        if (jobAdvert.isActivityStatus() == true) {
            jobAdvert.setAdStatusDescription("ADMİN TARAFINDAN KALDIRILDI");
            jobAdvert.setActivityStatus(false);
            this.jobAdvertDao.save(jobAdvert);
            return new SuccessResult("İlan yayından kaldırıldı");
        } else if (jobAdvert.isActivityStatus() == false) {
            return new ErrorResult("İlan zaten yayında değil");
        }
        return new ErrorResult("Hata");
    }

    @Override
    public Result confirmAdvert(int jobAdvertId) {
        var jobAdvert = this.jobAdvertDao.getByJobAdvertId(jobAdvertId);
        if (jobAdvert.isActivityStatus() == true) {
            return new ErrorResult("İlan zaten yayında");
        }
        //New Implementation
        Skills skills = jobAdvert.getSkills();

        List<String> eligibleUserEmails = userService.getAll().getData()
                .stream()
                .map(user -> user.getEmail()) // Extract email from user object
                .collect(Collectors.toList());
        emailEligiblePreference.sendJobPostNotification(eligibleUserEmails,jobAdvert);
        //checkPreferenceswithAllUser(skills,jobAdvert);
        jobAdvert.setAdStatusDescription("YAYINDA");
        jobAdvert.setActivityStatus(true);
        this.jobAdvertDao.save(jobAdvert);
        return new SuccessResult("İlan onaylandı");
    }
    //java,springboot,react,redux
    //java, Spring boot ,React ,Redux
    private void checkPreferenceswithAllUser(Skills skills,JobAdvert jobAdvert) {
        String[] skill = skills.getSkills().split(",");
        String[] aSkills = skills.getAdditionalSkills().split(",");

        // Trim whitespace from each skill and additional skill
        List<String> trimmedSkills = Arrays.stream(skill).map(String::trim).collect(Collectors.toList());
        List<String> trimmedAdditionalSkills = Arrays.stream(aSkills).map(String::trim).collect(Collectors.toList());

        List<User> users = userService.getAll().getData();
        List<String> eligibleUserEmails = new ArrayList<>();

        for (User u : users) {
            int count = 0;
            String[] strings = u.getPreference().getSkills().split(",");
            int totalSkills = strings.length;
            String[] adStrings = u.getPreference().getAdditionalSkills().split(",");
            totalSkills += adStrings.length;

            for (String s : strings) {
                if (trimmedSkills.contains(s.trim())) {
                    count++;
                }
            }
            for (String s : adStrings) {
                if (trimmedSkills.contains(s.trim())) {
                    count++;
                }
            }

            // Calculate the percentage of matching skills
            double matchingPercentage = (count * 100.0) / totalSkills;

            // Eligible if matching percentage is above 50%
            if (matchingPercentage > 50.0) {
                eligibleUserEmails.add(u.getEmail());
            }
        }
        emailEligiblePreference.sendJobPostNotification(eligibleUserEmails,jobAdvert);
    }

    @Override
    public DataResult<List<JobAdvert>> getAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "advertDate");
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.findAll(sort), "İlanlar tablosunun bütün verileri listelendi");
    }

    @Override
    public DataResult<List<JobAdvert>> getActiviteAdvertsByAdvertDateDesc() {
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getByActivityStatusIsTrueOrderByAdvertDateDesc(), "İlanlar tablosunun aktif bütün ilanları tarihi azalan sırayla listelendi");
    }

    @Override
    public DataResult<List<JobAdvert>> getActiviteAdvertsByAdvertDateDesc(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getByActivityStatusIsTrueOrderByAdvertDateDesc(pageable), "İlanlar tablosunun aktif bütün ilanları tarihi azalan sırayla listelendi");
    }

    @Override
    public DataResult<List<JobAdvert>> getByUserIdAndSortByAdvertDateDesc(int userId) {
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getByEmployer_UserIdOrderByAdvertDateDesc(userId), "Seçtiğiniz firmaya ait ilanlar listelendi");
    }

    @Override
    public DataResult<List<JobAdvert>> getByCompanyNameAndSortByAdvertDateDesc(String companyName) {
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getByEmployer_CompanyNameOrderByAdvertDateDesc(companyName), companyName + " Firmasının ilanları listelendi");
    }

    @Override
    public DataResult<List<JobAdvert>> getAllByCity_CityName(String cityName) {
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getAllByCity_CityName(cityName));
    }

    @Override
    public DataResult<List<JobAdvert>> getAllByCity_CityId(int cityId) {
        return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getAllByCity_CityId(cityId));
    }

    @Override
    public DataResult<JobAdvert> getByJobAdvertId(int jobAdvertId) {
        return new SuccessDataResult<JobAdvert>(this.jobAdvertDao.getByJobAdvertIdAndActive(jobAdvertId));
    }

    @Override
    public DataResult<List<JobAdvert>> getAllByPositionIdAndCityIdAndPlaceTypeIdAndTimeTypeIdAndActivityStatusIsTrueOrderByAdvertDateDesc
            (int positionId, int cityId, int placeTypeId, int timeTypeId) {
        return new SuccessDataResult<List<JobAdvert>>
                (this.jobAdvertDao.getAllByPositionIdAndCityIdAndPlaceTypeIdAndTimeTypeIdAndActivityStatusIsTrueOrderByAdvertDateDesc(positionId, cityId, placeTypeId, timeTypeId));
    }


    private Result CheckIfSalary(JobAdvert jobAdvert) {
        if (jobAdvert.getMinSalary() > jobAdvert.getMaxSalary()) {
            return new ErrorResult("Minimum maaş Maximum maaştan yüksek olamaz!");
        }
        return new SuccessResult();
    }


}

