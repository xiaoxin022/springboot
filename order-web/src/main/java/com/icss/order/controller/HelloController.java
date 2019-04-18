package com.icss.order.controller;

import com.icss.order.pojo.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    Tweet tweet1;
    @Autowired
    Tweet tweet2;

    @RequestMapping("/")
    public String home(){
        return "searchPage";
    }
    @RequestMapping("/result")
    public String hello(@RequestParam(value ="search",defaultValue = "springmvc") String search, Model model){
        List<Tweet> list = new ArrayList<Tweet>();
        list.add(tweet1);
        list.add(tweet2);
        model.addAttribute("tweets",list);
        model.addAttribute("search",search);
        return "resultPage";
    }
    @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String search = request.getParameter("search");
        if (search.toLowerCase().contains("习近平")){
            redirectAttributes.addFlashAttribute("error","根据国家法律不允许搜索");
            redirectAttributes.addFlashAttribute("search",search);
            return  "redirect:/";
        }
        redirectAttributes.addAttribute("search",search);
        return "redirect:result";
    }
}
