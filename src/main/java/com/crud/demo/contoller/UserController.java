package com.crud.demo.contoller;

import com.crud.demo.pojo.User;
import com.crud.demo.utils.Insert;
import com.crud.demo.utils.Result;
import com.crud.demo.utils.ResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @Author: zxx
 * @Date: 2018/11/28 20:49
 * @Version 1.0
 */
@Controller
public class UserController {
    @RequestMapping("/index")
    public String initList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User user1 = new User(1, "zhangsan", 26, "13855554545", "609747994@qq.com", "aa.jpg");
        User user2 = new User(2, "zhangsan", 26, "13855554545", "609747994@qq.com", "bb.jpg");
        User user3 = new User(3, "zhangsan", 26, "13855554545", "609747994@qq.com", "cc.jpg");
        User user4 = new User(4, "zhangsan", 26, "13855554545", "609747994@qq.com", "dd.jpg");
        User user5 = new User(5, "zhangsan", 26, "13855554545", "609747994@qq.com", "ee.jpg");
        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        session.setAttribute("num", userList.size());
        session.setAttribute("userList", userList);
        model.addAttribute("userList", userList);
        return "index";
    }

    @RequestMapping("/show")
    public String show(HttpServletRequest request, Model model, String lang) {
        final String staticLang1 = "1";
        final String staticLang2 = "2";
        HttpSession session = request.getSession();
        List<User> userList = (List<User>) session.getAttribute("userList");
        model.addAttribute("userList", userList);
        if (staticLang1.equals(lang)) {
            // 代码中即可通过以下方法进行语言设置
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                    new Locale("zh", "CN"));
        } else if (staticLang2.equals(lang)) {
            // 代码中即可通过以下方法进行语言设置
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
                    new Locale("en", "US"));
        }
        return "index";
    }

    @RequestMapping("/add")
    public String addShow(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "add";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestParam(value = "file", required = false) MultipartFile file, Model model,
                          @Validated({Insert.class}) User user, BindingResult result, HttpServletRequest request) {
        List<FieldError> errors = result.getFieldErrors();
        System.out.println(errors);
        for (FieldError fieldError : errors) {
            System.out.println(fieldError.getField() + fieldError.getDefaultMessage());
        }
        if (result.hasFieldErrors()) {
            return "add";
        }
        HttpSession session = request.getSession();
        List<User> userList = (List<User>) session.getAttribute("userList");
        String path = "D:/static/imgs/";

        userList.add(user);
        session.setAttribute("num", user.getId());
        // 文件处理
        String fileName = file.getOriginalFilename();
        fileName = user.getId() + fileName;
        user.setImg(fileName);
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            System.out.println("创建" + targetFile.isDirectory());
            System.out.println(targetFile.mkdirs());
        }
        try {
            file.transferTo(new File(path + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("userList", userList);
        model.addAttribute("userList", userList);
        return "redirect:/show";
    }
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<User> userList = (List<User>) session.getAttribute("userList");
        for (User user : userList) {
            if (user.getId() == id) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "add";
    }
    @RequestMapping("/editUser")
    public String editUser(@RequestParam(value = "file", required = false) MultipartFile file, Model model,
                           @Validated User user, BindingResult result, HttpServletRequest request){
        List<FieldError> errors = result.getFieldErrors();
        System.out.println(errors);
        for (FieldError fieldError : errors) {
            System.out.println(fieldError.getField() + fieldError.getDefaultMessage());
        }
        if (result.hasFieldErrors()) {
            return "add";
        }
        int index = 0;
        String path = "D:/static/imgs/";
        // 给user添加链接
        String fileName = file.getOriginalFilename();
        // 处理session
        HttpSession session = request.getSession();
        List<User> userList = (List<User>) session.getAttribute("userList");
        //遍历寻找对应的数据
        for (User userBean : userList) {
            if (user.getId() == userBean.getId()) {
                index = userList.indexOf(userBean);
                if (file.isEmpty()) {
                    user.setImg(userBean.getImg());
                } else {
                    // 文件处理
                    fileName = user.getId() + fileName;
                    user.setImg(fileName);
                    File targetFile = new File(path);
                    if (!targetFile.exists()) {
                        System.out.println("创建" + targetFile.isDirectory());
                        System.out.println(targetFile.mkdirs());
                    }
                    try {
                        file.transferTo(new File(path + fileName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                userList.remove(index);
                userList.add(index, user);
                break;
            }
        }
        session.setAttribute("userList", userList);
        model.addAttribute("userList", userList);
        return "redirect:/show";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Result deleteJson(int id, HttpServletRequest request, Model model) {
        int index = 0;
        HttpSession session = request.getSession();
        List<User> userList = (List<User>) session.getAttribute("userList");
        for (User user : userList) {
            if (user.getId() == id) {
                index = userList.indexOf(user);
                System.out.println(index);
                userList.remove(index);
                break;
            }
        }
        session.setAttribute("userList", userList);
        model.addAttribute("userList", userList);
        return ResultUtils.Success("删除成功");
    }

}
