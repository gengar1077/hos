package com.example.hos.service;

import com.example.hos.model.vo.ResultResponse;

/**
 * @author heweiwei
 * @date 2018/5/22
 */
public interface ResponseService {

    String message(String code);

    ResultResponse fail(String code);

    ResultResponse fail(String operator, String code);
}
