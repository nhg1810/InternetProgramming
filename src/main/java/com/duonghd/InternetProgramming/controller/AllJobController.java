package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Member;
import com.duonghd.InternetProgramming.responsitories.AccountResponeitory;
import com.duonghd.InternetProgramming.responsitories.JobReponsitory;
import com.duonghd.InternetProgramming.responsitories.MemberReponsitory;
import com.duonghd.InternetProgramming.responsitories.RoomReponsitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/moi-nguoi")
public class AllJobController {
    private JobReponsitory jobReponsitory;
    private RoomReponsitory roomReponsitory;
    private AccountResponeitory accountResponeitory;
    private MemberReponsitory memberReponsitory;
    @GetMapping("")
    public String index(Model model){
        List<Job> job = jobReponsitory.findAll();
        model.addAttribute("job", job);
        return "social";
    }
    @GetMapping("/chi-tiet/{idJob}")
    public String detail(@PathVariable Long idJob, Model model,
                         @CookieValue(name="idUser",defaultValue ="0") Long idCkUser){
        Job jobDetail = jobReponsitory.getOne(idJob);
        List<Job> jobHost = jobReponsitory.findByAccount(jobReponsitory.getOne(idJob).getAccount());
        Long idHost = jobDetail.getAccount().getIdAccount();
        if(idHost  == idCkUser){
            model.addAttribute("hoster","Là hoster");
        }else{
//            Member member = memberReponsitory.getOne(jobDetail.getMember().getIdMb());
                if(memberReponsitory.findByAccountAndJob(accountResponeitory.getOne(idCkUser),jobReponsitory.getOne(idJob)) != null){
                if(memberReponsitory.findByAccountAndJob(accountResponeitory.getOne(idCkUser),jobReponsitory.getOne(idJob)).getStatus()== 1){
                    model.addAttribute("noaccept","Chưa được duyệt");
                }else{
                    model.addAttribute("member","Thành viên");
                }
            }else{
                model.addAttribute("user","Chưa đăng ký");
            }
        }
        model.addAttribute("jobDetail", jobDetail);
        model.addAttribute("allJobHost",jobHost);
        return "detail-job";
    }
    @GetMapping("/dang-ki-tham-gia-nhom/{idJob}")
    public String JoinInGroup(@PathVariable Long idJob, Model model,
                              @CookieValue(name="idUser",defaultValue ="0") Long idCkUser,
                              RedirectAttributes attributes){
        if(idCkUser == 0){
            return "redirect:/trang-chu/dang-nhap";
        }
        //muốn đăng kí tham gia phải điền đẩy đủ thông tin profile trc đã
        if(accountResponeitory.getOne(idCkUser).getMoreInfAccount() == null){
            return "redirect:/thong-tin-ca-nhan/cua-toi";
        }
        Member member = new Member();
        member.setJob(jobReponsitory.getOne(idJob));
        member.setAccount(accountResponeitory.getOne(idCkUser));
        member.setStatus(1);

        memberReponsitory.save(member);
        return "redirect:/moi-nguoi/chi-tiet/"+ idJob;
    }
    @GetMapping("/duyet-thanh-vien/{idMember}/{idJob}")
    public String AcceptMember(@PathVariable String idMember,@PathVariable Long idJob,
                               Model model, @CookieValue(name="idUser",defaultValue ="0") Long idHost){
       Long idHostInJob =  jobReponsitory.getOne(idJob).getAccount().getIdAccount();
       if(idHostInJob == idHost){
           Member updateMember = new Member();
           updateMember.setIdMb(memberReponsitory.getOne(idMember).getIdMb());
           updateMember.setStatus(2);
           updateMember.setAccount(memberReponsitory.getOne(idMember).getAccount());
           updateMember.setJob(memberReponsitory.getOne(idMember).getJob());
           memberReponsitory.save(updateMember);
           return "redirect:/quan-ly-phong-hop/chi-tiet-cong-viec/"+ idJob;
       }
       return "redirect:/moi-nguoi/chi-tiet/"+ idJob;
    }
    @GetMapping("/chan-thanh-vien/{idMember}/{idJob}")
    public String KickMember(@PathVariable String idMember,@PathVariable Long idJob,
                               Model model, @CookieValue(name="idUser",defaultValue ="0") Long idHost){
        Long idHostInJob =  jobReponsitory.getOne(idJob).getAccount().getIdAccount();
        if(idHostInJob == idHost){
            Member updateMember = new Member();
            updateMember.setIdMb(memberReponsitory.getOne(idMember).getIdMb());
            updateMember.setStatus(1);
            updateMember.setAccount(memberReponsitory.getOne(idMember).getAccount());
            updateMember.setJob(memberReponsitory.getOne(idMember).getJob());
            memberReponsitory.save(updateMember);
            return "redirect:/quan-ly-phong-hop/chi-tiet-cong-viec/"+ idJob;
        }
        return "redirect:/moi-nguoi/chi-tiet/"+ idJob;

    }
    @GetMapping("/cac-khoa-da-tham-gia")
    public String TutorialJoin(@CookieValue(name="idUser",defaultValue ="0") Long idCkUser
            ,Model model){
        if(idCkUser == 0){
            return "redirect:/trang-chu/dang-nhap/";
        }
        List<Member> myTutorial = memberReponsitory.findByAccount(accountResponeitory.getOne(idCkUser));
        model.addAttribute("myTutorial",myTutorial);
        return "myTutorial";
    }

}
