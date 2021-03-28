package com.example.hos.controller;

import com.example.hos.model.vo.ResultResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author changwei.zhong
 * @date create by 2021/2/24
 */
@Controller
@RequestMapping("/role")
public class RoleController {
//    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @GetMapping(value = "/info")
    @ResponseBody
    public ResultResponse roleInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "role");
        map.put("val", "role/info");
        ResultResponse response = new ResultResponse();
        response.setReturnData(map);
        return response;
    }
}
