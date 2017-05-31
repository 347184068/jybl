package com.wfu.modules.adsync.config;

import com.wfu.common.config.Global;

/**
 * Created by Admin on 2017/2/21.
 * AD域参数配置
 */
public class AdConfig {
    private String url;
    private String ldapfactory;
    private String authentication;
    private String org;
    private String user;
    private String password;
    private static AdConfig adConfig = null;

    public AdConfig() {
        url = Global.getConfig("ldap.url");
        ldapfactory = Global.getConfig("ldap.ldapfactory");
        authentication = Global.getConfig("ldap.authentication");
        org = Global.getConfig("ldap.org");
        user = Global.getConfig("ldap.user");
        password = Global.getConfig("ldap.password");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLdapfactory() {
        return ldapfactory;
    }

    public void setLdapfactory(String ldapfactory) {
        this.ldapfactory = ldapfactory;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static AdConfig getAdConfig() {
        if (adConfig == null){
            adConfig = new AdConfig();
        }
        return adConfig;
    }


}
