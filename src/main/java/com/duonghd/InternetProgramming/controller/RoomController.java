package com.duonghd.InternetProgramming.controller;

import com.duonghd.InternetProgramming.entity.Account.Account;
import com.duonghd.InternetProgramming.entity.Account.Job;
import com.duonghd.InternetProgramming.entity.Account.Message;
import com.duonghd.InternetProgramming.entity.Account.Room;
import com.duonghd.InternetProgramming.responsitories.*;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/quan-ly-phong-hop")
public class RoomController {
    private JobReponsitory jobReponsitory;
    private AccountResponeitory accountResponeitory;
    private MessageReponsitory messageReponsitory;
    private RoomReponsitory roomReponsitory;
    private MemberReponsitory memberReponsitory;
    @GetMapping("")
    public String index(ModelAndView modelAndView,
                        @CookieValue(name="idUser",defaultValue ="0") Long cookies,
                        Model model){
        if(cookies == 0l){
            return "redirect:/trang-chu/dang-nhap";
        }
        //in ra các phòng của người dùng hoặc các khoá đã đăng kí
        model.addAttribute("job",jobReponsitory.findByAccount(accountResponeitory.getOne(cookies)));
        return "all-room";
    }

//    check xem trong bang member da ton tai user nay chua
    @GetMapping("/{idRoom}")
    public String message(@PathVariable String idRoom ,
                          @CookieValue(name="idUser",defaultValue ="0") Long cookies,
                          HttpServletResponse response,

                          Model model)throws NoSuchAlgorithmException {
        if(cookies == 0L){
            return "redirect:/trang-chu/dang-nhap";
        }
        Room room = roomReponsitory.getOne(idRoom);
        List<Message> message = messageReponsitory.findByRoom(room);
//        nếu là host thì show ra các job của host
        Job job = jobReponsitory.findByRoom(room);
        if(cookies == jobReponsitory.findByRoom(roomReponsitory.getOne(idRoom)).getAccount().getIdAccount()){
            model.addAttribute("job",jobReponsitory.findByAccount(accountResponeitory.getOne(cookies)));
        }else{
            //check xem phải là thành viên của job mới vào đc phòng chat
            if(memberReponsitory.findByAccountAndJob(accountResponeitory.getOne(cookies), job) != null){
                model.addAttribute("job",jobReponsitory.findByAccount(accountResponeitory.getOne(cookies)));
            }else{
                return "redirect:/trang-chu/";
            }
        }
        Cookie newCookie = new Cookie("idRoom",job.getRoom().getIdRoom());
        newCookie.setMaxAge(24*60*60);
//        newCookie.setSecure(true);
//        newCookie.setHttpOnly(true);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        model.addAttribute("jobname",job.getNameJob());
        model.addAttribute("idroom",job.getRoom().getIdRoom());
        model.addAttribute("imgjob",job.getContext());
        model.addAttribute("message",message);
        model.addAttribute("idUser", cookies);
        return "room";
    }
    @GetMapping("/rtc/{idRoom}")
    public String callVideo(@PathVariable String idRoom, Model model
                            , @CookieValue(name="idUser",defaultValue ="0") Long idUser,
                            RedirectAttributes attributes){

        //check condition in here
        //check no login
        if(idUser == 0L){
            return "redirect:/trang-chu/dang-nhap";
        }
        Room room = roomReponsitory.getOne(idRoom);
        if(room.getTokenRoom() == null){
            return "video-call";
        }
        model.addAttribute("tokenRoom",room.getTokenRoom());
        return "video-called";
    }
    @GetMapping("/ket-thuc")
    public String closeMeet(RedirectAttributes attributes,
                            @CookieValue(name="tokenRoom",defaultValue ="0") String tookenRoom,
                            @CookieValue(name="idRoom",defaultValue ="0") String idRoom,
                            HttpServletResponse response){
        //check xem phải user trong room không để đc vô họp
        Room room = new Room();
        room.setIdRoom(idRoom);
        room.setTokenRoom(null);
        roomReponsitory.save(room);

        Cookie newCookie = new Cookie("tokenRoom","");
        newCookie.setMaxAge(0);
        newCookie.setPath("/");
        response.addCookie(newCookie);

        return "redirect:/quan-ly-phong-hop/rtc/"+idRoom+"";
    }
    @GetMapping("/chi-tiet-cong-viec/{idJob}")
    public String detailJob(@PathVariable Long idJob,
                            @CookieValue(name="idUser",defaultValue ="0") Long idUser,
                            Model model){
        Account account = accountResponeitory.getOne(idUser);
        Job jobDetail = jobReponsitory.findByIdJobAndAccount(idJob, account);
        if(jobDetail != null){
            model.addAttribute("jobDetail",jobDetail);
            model.addAttribute("member",jobDetail.getMembers());
            return "detail-your-job";
        }
        return "redirect:/trang-chu/dang-nhap";
    }
}
