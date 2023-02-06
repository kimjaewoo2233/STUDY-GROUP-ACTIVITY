package org.hotel.back.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hotel.back.data.request.RegisterData;
import org.hotel.back.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginGET(@RequestParam(required = false) String err,
                           Model model){
            if (err != null){
                model.addAttribute("err","err");
            }
            return "login";
    }

    @GetMapping("/register")
    public String registerGET(){

        return "register";
    }


    @PostMapping("/register")
    public String registerPOST(@Valid RegisterData data,
                               RedirectAttributes redirectAttributes,
                               BindingResult bindingResult){
        log.info("POST REGISTER DATA: {}",data);

        memberService.registerSave(data);
        return "redirect:login";
    }


    @ExceptionHandler(BindException.class)
    public String registerException(BindingResult bindingResult,RedirectAttributes redirectAttributes){
        List<String> list = bindingResult.getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        redirectAttributes.addFlashAttribute("errorList",list);
        return "redirect:register";
    }
}
