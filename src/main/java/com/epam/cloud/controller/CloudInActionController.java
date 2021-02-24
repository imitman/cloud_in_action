package com.epam.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "CloudInActionController")
@Controller
public class CloudInActionController {

    @ApiOperation(value = "Gets welcome page")
    @GetMapping("/welcome")
    public String homePage(
            @ApiParam(value = "User name, optional")
            @RequestParam(required = false, defaultValue = "Guest") String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }
}
