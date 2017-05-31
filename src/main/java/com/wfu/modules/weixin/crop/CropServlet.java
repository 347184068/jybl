package com.wfu.modules.weixin.crop;

import com.github.sd4324530.fastweixin.message.aes.AesException;
import com.github.sd4324530.fastweixin.message.aes.WXBizMsgCrypt;
import com.wfu.common.config.Global;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by tepusoft on 2017/4/11.
 */
@SuppressWarnings("serial")
public class CropServlet extends HttpServlet {
    String sToken = Global.getConfig("Corp_token");
    String sCorpID = Global.getConfig("CorpId");
    String sEncodingAESKey = Global.getConfig("Corp_EncodingAESKey");

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WXBizMsgCrypt wxcpt;
        try {
            wxcpt = new WXBizMsgCrypt(sToken, sEncodingAESKey, sCorpID);
            String sVerifyMsgSig = request.getParameter("msg_signature");

            String sVerifyTimeStamp = request.getParameter("timestamp");

            String sVerifyNonce = request.getParameter("nonce");

            String sVerifyEchoStr = request.getParameter("echostr");
            String sEchoStr;

            sEchoStr = wxcpt.verifyUrl(sVerifyMsgSig, sVerifyTimeStamp,
                    sVerifyNonce, sVerifyEchoStr);

            System.out.println("verifyurl echostr: " + sEchoStr);
            PrintWriter out = response.getWriter();
            out.print(sEchoStr);
            out.close();
            out = null;
        }
        catch (AesException e) {
            e.printStackTrace();
        }
    }
}
