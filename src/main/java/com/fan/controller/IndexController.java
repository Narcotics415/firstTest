package com.fan.controller;

import com.fan.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Resource
    private UserService userService;

    @RequestMapping("/adminLogin")
    public String adminLogin(){
        return "adminLogin";
    }

    @RequestMapping("/waiterLogin")
    public String waiterLogin(){
        return "waiterLogin";
    }

    @RequestMapping("/userLogin")
    public String userLogin(){
        return "userLogin";
    }

    @RequestMapping("/userQueue")
    public String userQueue(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") != null){
            int queueSize = userService.countBeforeQueue((int)session.getAttribute("uid"));
            int eatFlag = userService.eatOrNot((int)session.getAttribute("uid"));
            System.out.println("uid :"+session.getAttribute("uid"));
            if (queueSize != -1){
                System.out.println("queueSize :"+queueSize);
                session.setAttribute("queueSize",queueSize);
            } else {
                System.out.println("eatFlag :"+eatFlag);

                session.setAttribute("eatFlag",eatFlag);
                session.setAttribute("noQueue",1);
            }
        }

        return "userQueue";
    }

}
