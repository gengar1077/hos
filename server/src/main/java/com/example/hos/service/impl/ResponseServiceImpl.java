package com.example.hos.service.impl;

import com.example.hos.log.LogFactory;
import com.example.hos.model.vo.ResultResponse;
import com.example.hos.service.ResponseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author heweiwei
 * @date 2018/5/22
 */
@Service
public class ResponseServiceImpl implements ResponseService {

    private Properties codeProperties;

    @PostConstruct
    public void init(){
        InputStream is=null;
        try{
            codeProperties=new Properties();
            is=this.getClass().getResourceAsStream("/codemessage.properties");
            codeProperties.load(is);
        } catch (IOException e) {
            LogFactory.getErrorLog().error(e.getMessage(),e);
            throw new RuntimeException(e);
        } finally {
//            IOCloseUtils.close(is);
        }
    }

    @Transactional
    @Override
    public String message(String code){
        return codeProperties.getProperty(code);
    }

    @Transactional
    @Override
    public ResultResponse fail(String code){
        LogFactory.getDebugLog().info("operate fail,result code "+code);
        ResultResponse resultResponse=new ResultResponse();
        String message=message(code);
        resultResponse.fail(code,message);
        return resultResponse;
    }

    @Transactional
    @Override
    public ResultResponse fail(String operator,String code){
        LogFactory.getDebugLog().info("operate fail,operator "+operator+",result code "+code);
        ResultResponse resultResponse=new ResultResponse();
        String message=message(code);
        resultResponse.fail(code,message);
        return resultResponse;
    }
}
