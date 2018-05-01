package com.xiaoyiyiyo.controller;

import com.xiaoyiyiyo.common.AuthConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoyiyiyo on 2018/5/1.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute(AuthConst.CLIENT_URL, request.getParameter(AuthConst.CLIENT_URL));
        return "index";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
