package com.fan.controller;


import com.fan.config.UserToken;
import com.fan.pojo.Desk;
import com.fan.pojo.Queue;
import com.fan.pojo.User;
import com.fan.service.WaiterService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 服务员身份管理
 *@author zyf
 */


@Controller
public class WaiterController {


    @Resource
    private WaiterService waiterService;

    //登录
    @RequestMapping("/w-main")
    public String toAdminMain(){
        return "waiterMain";
    }

    @RequestMapping("/waiter/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){

        /*  shiro方法 */
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        UserToken token = new UserToken(username,password,"Waiter");

        try {
            subject.login(token);//执行登录的方法，如果没有异常就ok
            return "redirect:/w-main";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误!");
            return "waiterLogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误!");
            return "waiterLogin";
        }


    }



    //w查询所有餐桌信息
    @RequestMapping("/w-desks")
    public String queryDeskList(Model model, HttpServletRequest request){

        Integer pageNo=request.getParameter("pageNo")==null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Desk> pageInfo = waiterService.queryDeskList(pageNo,10);
        model.addAttribute("list",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNo",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        return "desk/w-list";
    }

    //餐桌更改
    @GetMapping("/w-desk/{id}")
    public String toUpdateDesk(@PathVariable("id") Integer id, Model model){
        Desk desk = waiterService.queryDeskById(id);
        User user = waiterService.queryUserByUid(desk.getUid());
        model.addAttribute("desk",desk);
        model.addAttribute("user",user);
        return "desk/w-update";
    }

    @PostMapping("/w-updateDesk")
    public String updateDesk(Desk desk){
        waiterService.updateDesk(desk);
        return "success";
    }

    //退桌 && 从队列中入座
    @GetMapping("/cleardesk/{id}")
    public String clearDesk(@PathVariable("id")Integer id){
        //退桌
        Desk desk = waiterService.queryDeskById(id);
        System.out.println(desk);
        desk.setUid(0);
        desk.setFlag(0);
        waiterService.updateDesk(desk);


        //让可入座的入座
        waiterService.beSeated();
        return "redirect:/w-desks";
    }


    ////w查询所有排队信息
    @RequestMapping("/queues")
    public String queryQueueList(Model model, HttpServletRequest request){

        Integer pageNo=request.getParameter("pageNo")==null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Queue> pageInfo = waiterService.queryQueueList(pageNo,10);
        model.addAttribute("list",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNo",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        return "queue/list";
    }

    //排队添加
    @GetMapping("/queue")
    public String toAddQueue(){
        return "queue/add";
    }

    @PostMapping("/queue")
    public String addQueue(Queue queue){
        waiterService.addQueue(queue);
        System.out.println(queue);
        return "success";
    }

    //排队信息删除
    @GetMapping("/delqueue/{id}")
    public String deleteUser(@PathVariable("id")Integer id){
        waiterService.deleteQueue(id);
        return "redirect:/queues";
    }




}
