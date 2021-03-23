//package com.example.hos.handle;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.FileNotFoundException;
//
///**
// * @author DBC-090
// */
//@ControllerAdvice //全局异常处理注释
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(FileNotFoundException.class)
//    public String fileNotFoundExceptionHander(Exception e){
//        log.error("全局异常处理，捕获到异常信息",e);
//        return "error/fileEx";
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exceptionHander(Exception e){
//        log.error("全局异常处理，捕获异常",e);
//        ModelAndView mv = new ModelAndView("error/error");
//        mv.addObject("errorMsg",e.getMessage());
//        return mv;
//    }
//}
