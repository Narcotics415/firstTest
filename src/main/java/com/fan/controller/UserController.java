package com.fan.controller;


import com.fan.config.UserToken;
import com.fan.pojo.Desk;
import com.fan.pojo.Queue;
import com.fan.pojo.User;
import com.fan.service.UserService;
import com.fan.service.WaiterService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {



    @Resource
    private UserService userService;


    //登录
    @GetMapping("/u-main")
    public String toUserMain(){
        return "userMain";
    }

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){
        //当前用户的id存入model
        int uid = userService.queryUserByName(username).getId();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("uid",uid);

        //获取是否排过队
        int eat = userService.eatOrNot(uid);
        int countBeforeQueue = userService.countBeforeQueue(uid);

        /*  shiro方法 */
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        UserToken token = new UserToken(username,password,"User");

        try {
            subject.login(token);//执行登录的方法，如果没有异常就ok
            if (eat == 1 || countBeforeQueue != -1){
                System.out.println("已经排过队！");
                return "/userQueue";
            }else
            return "redirect:/u-main";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误!");
            return "userLogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误!");
            return "userLogin";
        }
    }

    //注册
    @GetMapping("/userRegister")
    public String toUserRegister(){
        return "userRegister";
    }

    @PostMapping("/user/regis")
    public String userRegister(User user){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        if (userService.queryUserByName(user.getName()) == null){
            userService.addUser(user);
            return "redirect:/userLogin";
        }else {
            session.setAttribute("same","该用户名已被使用！");
            return "redirect:/userRegister";
        }
    }

    //进入排队序列
    @PostMapping("/user/queue")
    public String userQueue(Queue queue){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        int uid = (int) session.getAttribute("uid");
        System.out.println("当前用户为：" + uid);
        List<Queue> queueList = userService.queryQueueList();
        List<Desk> deskList = userService.queryFreeDeskList();
        int flag = 0;

        if(userService.goSeatedOrNot(queue.getNum())==1){
            //如果能入座，直接入座无需排队
            System.out.println("直接入座！");
            userService.goSeated(queue.getNum(),uid);
            flag = 1;
        }else{
            System.out.println("进入等待队列！");
            session.setAttribute("queueSize",queueList.size());
            userService.addQueue(queue);
            flag = 2;
        }
        session.setAttribute("flag",flag);
        return "userQueue";
    }

    //结束  退出登录 清空session
    @RequestMapping("/user/logout")
    public String userLogout(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        //情况判断
        int uid = (int)session.getAttribute("uid");
        Desk desk = userService.queryDeskByUid(uid);
        Queue queue = userService.queryQueueByUid(uid);
        if(desk!=null){ //退桌
            System.out.println("获取的退桌uid："+uid);
            System.out.println(desk);
            desk.setUid(0);
            desk.setFlag(0);
            userService.updateDesk(desk);
            session.removeAttribute("uid");session.removeAttribute("queueSize");
            session.removeAttribute("flag");session.removeAttribute("eatFlag");
            return "redirect:/";
        }else if (queue!=null){//删除排队
             System.out.println("要删除的队列"+queue);
             userService.deleteQueueByUid(uid);
             session.removeAttribute("uid");session.removeAttribute("queueSize");
             session.removeAttribute("flag");session.removeAttribute("eatFlag");
             return "redirect:/";
         }
         else if (uid>0){//清空session
            session.removeAttribute("uid");session.removeAttribute("queueSize");
            session.removeAttribute("flag");session.removeAttribute("eatFlag");
            return "redirect:/";
        }else{//直接返回
            return "redirect:/";
        }
    }
}
