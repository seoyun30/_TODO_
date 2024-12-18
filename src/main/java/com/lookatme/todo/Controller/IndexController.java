package com.lookatme.todo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//시작페이지(main)를 작업할 controller
@Controller
public class IndexController {
    @GetMapping({"/","/index"})
    public String index(){
        return "redirect:/a"; //"index";
    }


}
