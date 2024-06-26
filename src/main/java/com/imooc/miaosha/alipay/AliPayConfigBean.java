package com.imooc.miaosha.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/pay/alipay.properties")
@ConfigurationProperties(prefix = "alipay")
@Data
public class AliPayConfigBean {
    // 商户appid
    private String appId;
    // 收款支付宝账号Id
    private String sellerId;
    // 私钥 pkcs8格式的
    private String privateKey;
    // 支付宝公钥
    private String publicKey;
    //网站域名
    private String domain;
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notify_url = "http://localhost:8080/";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，
    // 必须外网可以正常访问 商户可以自定义同步跳转地址
    private String return_url = "http://localhost:8080/";
    // 请求网关地址
    private String serverUrl = "https://openapi.pay.com/gateway.do";
    // 日志记录目录
    private String log_path = "/log";
    // RSA2
    private String SIGNTYPE = "RSA2";
    // 编码
    private String CHARSET = "UTF-8";
    // 返回格式
    private String FORMAT = "json";

    @Override
    public String toString() {
        return "AliPayConfig [appId=" + appId + ", sellerId=" + sellerId +", privateKey=" + privateKey + ", publicKey=" + publicKey + ", serverUrl="
                + serverUrl + ", domain=" + domain + ", log_path=" + log_path+", SIGNTYPE=" + SIGNTYPE+", CHARSET=" + CHARSET+
                ", FORMAT=" + FORMAT+", notify_url=" + notify_url+", return_url=" + return_url+"]";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

    public String getSIGNTYPE() {
        return SIGNTYPE;
    }

    public void setSIGNTYPE(String SIGNTYPE) {
        this.SIGNTYPE = SIGNTYPE;
    }

    public String getCHARSET() {
        return CHARSET;
    }

    public void setCHARSET(String CHARSET) {
        this.CHARSET = CHARSET;
    }

    public String getFORMAT() {
        return FORMAT;
    }

    public void setFORMAT(String FORMAT) {
        this.FORMAT = FORMAT;
    }
}
