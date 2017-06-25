package com.wfu.modules.weixin.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.wfu.common.utils.DateUtils;
import com.wfu.modules.weixin.entity.WxUser;
import com.wfu.modules.weixin.service.WxUserService;
import com.wfu.modules.weixin.util.WebAPI;

/**
 * Created by wwhui on 2016-4-14.
 */
@Component
public class SyncWxUserInfoJob {
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private WxUserService wxUserService;
    private UserAPI userAPI=new UserAPI(WebAPI.getConfig());


    public SyncWxUserInfoJob(){}

    public void syncWxinUserInfo(String openId){
        taskExecutor.execute(new WxUserInfoThread(openId));
    }
    class WxUserInfoThread implements Runnable {
        private String openId;
        public WxUserInfoThread(String openId){
            this.openId=openId;
        }
        @Override
        public void run() {
            GetUserInfoResponse user=userAPI.getUserInfo(openId);
            WxUser wxUser= wxUserService.getByOpenId(openId);
            //WxUser wxUser=new WxUser();
            wxUser.setHeadimgurl(user.getHeadimgurl());
            wxUser.setNickname(user.getNickname());
            wxUser.setSex(user.getSex());
            wxUser.setCity(user.getCity());
            wxUser.setProvince(user.getProvince());
            wxUser.setCountry(user.getCountry());
            wxUser.setSubscribeTime(DateUtils.paseDateByLong(user.getSubscribeTime()));
            wxUser.setRemark(user.getRemark());
            wxUser.setGroupid(user.getGroupid());
            wxUserService.save(wxUser);
        }
    }

}
