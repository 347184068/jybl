package com.wfu.modules.app.web;

import com.wfu.common.web.BaseController;
import com.wfu.modules.app.service.BookUserService;
import org.omg.CORBA.BAD_CONTEXT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static oracle.net.aso.C05.c;

/**
 * @Author XuYunXuan
 * @Date 2017/6/25 15:38
 */

@Controller
@RequestMapping("/app")
public class APPLoginController extends BaseController {

    @Autowired
    private BookUserService bookUserService;


    @RequestMapping(value = "login")
    public void login(){

    }

    @RequestMapping(value = "getBorrowInfo")
    public void queryBookInfo(){

    }


    @RequestMapping(value = "borrowBook")
    public void borrrowBook(){

    }

    @RequestMapping(value = "returnConfirm")
    public void confirmBookReturn(){

    }

}
