package com.easymail.easymail.controller;


import com.easymail.easymail.entity.Account;
import com.easymail.easymail.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    AccountMapper accountMapper;

    @GetMapping("/login")
    public String getLogin(Model model,RedirectAttributes redirectAttributes,HttpSession session){
        if(session.getAttribute("CURRENT_USER")!=null){
            redirectAttributes.addFlashAttribute("message","您已经登录，请在个人中心注销后再进行登录！");
            return "redirect:/mail";
        }
        model.addAttribute("account",new Account());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@RequestParam("next") Optional<String> next, Account account, BindingResult result, HttpSession session, final RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("account", account);
            return "login";
        }
        Account account1= accountMapper.findAccountByAddress(account.getAddress());
        if(account1!=null && account1.getPassword().equals(account.getPassword())){
            session.setAttribute("CURRENT_USER", account1);
            session.setMaxInactiveInterval(30*60);
            redirectAttributes.addFlashAttribute("message","登录成功");
            return "redirect:".concat(next.orElse("/mail"));
        }
        else{
            redirectAttributes.addFlashAttribute("message", "登录失败,请重新登录!");
            return "redirect:/login";
        }
    }
}
