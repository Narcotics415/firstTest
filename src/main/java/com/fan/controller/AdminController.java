package com.fan.controller;


import com.fan.config.UserToken;
import com.fan.pojo.Desk;
import com.fan.pojo.User;
import com.fan.pojo.Waiter;
import com.fan.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 管理员身份管理
 *@author zyf
 */


@Controller
public class AdminController {


    @Resource
    private AdminService adminService;

    //登录
    @RequestMapping("/a-main")
    public String toAdminMain(){
        return "adminMain";
    }

    @RequestMapping("/admin/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model){
        /*  shiro方法 */
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户登录数据
        UserToken token = new UserToken(username,password,"Admin");

        try {
            subject.login(token);//执行登录的方法，如果没有异常就ok
            return "redirect:/a-main";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误!");
            return "adminLogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误!");
            return "adminLogin";
        }
    }

    //桌面
    @RequestMapping("/welcome")
    public  String welcome(){
        return "welcome";
    }


    ////admin查询所有用户信息
    @RequestMapping("/users")
    public String queryUserList(Model model, HttpServletRequest request){

        Integer pageNo=request.getParameter("pageNo")==null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<User> pageInfo = adminService.queryUserList(pageNo,10);
        model.addAttribute("list",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNo",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        return "user/list";
    }

    //用户添加
    @GetMapping("/user")
    public String toAddpage(){
        return "user/add";
    }

    @PostMapping("/user")
    public String addUser(User user){
        adminService.addUser(user);
        System.out.println(user);
        return "success";
    }

    //用户更改
    @GetMapping("/user/{id}")
    public String toUpdateUser(@PathVariable("id") Integer id, Model model){
        User user = adminService.queryUserById(id);
        model.addAttribute("user",user);
        return "user/update";
    }

    @PostMapping("/updateUser")
    public String updateUser(User user){
        adminService.updateUser(user);
        return "success";
    }

    //用户删除
    @GetMapping("/deluser/{id}")
    public String deleteUser(@PathVariable("id")Integer id){
        adminService.deleteUser(id);
        return "redirect:/users";
    }


    ////admin查询所有服务生信息
    @RequestMapping("/waiters")
    public String queryWaiterList(Model model, HttpServletRequest request){

        Integer pageNo=request.getParameter("pageNo")==null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Waiter> pageInfo = adminService.queryWaiterList(pageNo,10);
        model.addAttribute("list",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNo",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        return "waiter/list";
    }

    //服务生添加
    @GetMapping("/waiter")
    public String toAddWaiter(){
        return "waiter/add";
    }

    @PostMapping("/waiter")
    public String addWaiter(Waiter waiter){
        adminService.addWaiter(waiter);
        System.out.println(waiter);
        return "success";
    }

    //服务生更改
    @GetMapping("/waiter/{id}")
    public String toUpdateWaiter(@PathVariable("id") Integer id, Model model){
        Waiter waiter = adminService.queryWaiterById(id);
        model.addAttribute("waiter",waiter);
        return "waiter/update";
    }

    @PostMapping("/updateWaiter")
    public String updateWaiter(Waiter waiter){
        adminService.updateWaiter(waiter);
        return "success";
    }

    //服务生删除
    @GetMapping("/delwaiter/{id}")
    public String deleteWaiter(@PathVariable("id")Integer id){
        adminService.deleteWaiter(id);
        return "redirect:/waiters";
    }



    ////admin查询所有餐桌信息
    @RequestMapping("/desks")
    public String queryDeskList(Model model, HttpServletRequest request){

        Integer pageNo=request.getParameter("pageNo")==null ? 1 : Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Desk> pageInfo = adminService.queryDeskList(pageNo,10);
        model.addAttribute("list",pageInfo.getList());
        model.addAttribute("pages",pageInfo.getPages());
        model.addAttribute("pageNo",pageInfo.getPageNum());
        model.addAttribute("pageSize",pageInfo.getPageSize());
        return "desk/list";
    }

    //餐桌添加
    @GetMapping("/desk")
    public String toAddDesk(){
        return "desk/add";
    }

    @PostMapping("/desk")
    public String addDesk(Desk desk){
        adminService.addDesk(desk);
        System.out.println(desk);
        return "success";
    }

    //餐桌更改
    @GetMapping("/desk/{id}")
    public String toUpdateDesk(@PathVariable("id") Integer id, Model model){
        Desk desk = adminService.queryDeskById(id);
        model.addAttribute("desk",desk);
        return "desk/update";
    }

    @PostMapping("/updateDesk")
    public String updateDesk(Desk desk){
        adminService.updateDesk(desk);
        return "success";
    }

    //餐桌删除
    @GetMapping("/deldesk/{id}")
    public String deleteDesk(@PathVariable("id")Integer id){
        adminService.deleteDesk(id);
        return "redirect:/desks";
    }





}
