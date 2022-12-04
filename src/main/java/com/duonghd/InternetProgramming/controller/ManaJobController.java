package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.*;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import com.duonghd.InternetProgramming.responsitories.JobReponsitory;
import com.duonghd.InternetProgramming.responsitories.MemberReponsitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Controller
@AllArgsConstructor
@RequestMapping("/quan-ly-cong-viec")
public class ManaJobController {
    private JobReponsitory jobReponsitory;
    private MemberReponsitory memberReponsitory;
    private AccountResponeitory accountResponeitory;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/uploads/job";

    @GetMapping("")
    public String index(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                              Model model,
                              RedirectAttributes attributes){
        if(cookies == 0l){
            return "redirect:/trang-chu/dang-nhap";
        }
//        model.addAttribute("member", memberReponsitory.findByJob(accountResponeitory.getOne(cookies)));
        model.addAttribute("job",jobReponsitory.findByAccount(accountResponeitory.getOne(cookies)));
        return "your-job";
    }

    @GetMapping("/tao-cong-viec")
    public ModelAndView CreateJob(ModelAndView modelAndView){
        modelAndView.setViewName("create-job");
        modelAndView.addObject("job",new Job());
        return modelAndView;
    }
    @PostMapping("/tao-cong-viec")
    public String PCreateJob(@CookieValue(name="idUser",defaultValue ="0") Long cookies,
                             @Valid @ModelAttribute("job") Job job, BindingResult result,
                             @RequestParam("image-job") MultipartFile file
            , RedirectAttributes attributes, Model model) throws IOException {


        if(cookies == 0l) {
            return "redirect:/trang-chu/dang-nhap";
        }
        if(file.getOriginalFilename() != ""){
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            String st_img_job = file.getOriginalFilename();
            job.setContext(st_img_job);
        }else{
            job.setContext("");
        }


        Account accountHost= accountResponeitory.getById(cookies);
        Room room = new Room();
        Member member = new Member();
        DetailJob detailJob = new DetailJob();
        Date date = new Date();

        detailJob.setDate(date);
        detailJob.setFrom(job.getDetailJob().getFrom());
        detailJob.setLanguage(job.getDetailJob().getLanguage());
        detailJob.setDescripts(job.getDetailJob().getDescripts());
        detailJob.setLevel(job.getDetailJob().getLevel());
        detailJob.setSkillApply(job.getDetailJob().getSkillApply());
        detailJob.setJob(job);



        job.setRoom(room);
        job.setAccount(accountHost);
        job.setDetailJob(detailJob);
        jobReponsitory.save(job);

        return "redirect:/quan-ly-cong-viec/tao-cong-viec";
    }
}

