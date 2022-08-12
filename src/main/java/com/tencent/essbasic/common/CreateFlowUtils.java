package com.tencent.essbasic.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import com.tencentcloudapi.essbasic.v20210526.models.FlowInfo;
import com.tencentcloudapi.essbasic.v20210526.models.UserInfo;

import java.util.Base64;
import java.util.Base64.Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.tencent.essbasic.config.*;

/**
 * 创建签署流程的一些工具类
 */

public class CreateFlowUtils {
    /**
     * 设置Agent
     *
     * @return Agent
     */
    public static Agent setAgent(){
        Agent agent = new Agent();
        UserInfo userInfo = new UserInfo();
        agent.setAppId(Config.AppId);
        agent.setProxyAppId(Config.ProxyAppId);
        agent.setProxyOrganizationOpenId(Config.ProxyOrganizationOpenId);
        userInfo.setOpenId(Config.ProxyOperatorOpenId);
        agent.setProxyOperator(userInfo);
        return agent;
    }

    /**
     * 设置FlowInfo
     *
     * @param TemplateId    模板唯一标识
     * @param FlowName 签署流程名称
     * @param flowApproverInfos 签署流程签约方列表
     * @return FlowInfo
     */
    public static FlowInfo fillFlowInfo(String TemplateId, String FlowName, FlowApproverInfo[] flowApproverInfos){
        FlowInfo flowInfo = new FlowInfo();
        flowInfo.setTemplateId(TemplateId);
        flowInfo.setFlowName(FlowName);
        flowInfo.setFlowApprovers(flowApproverInfos);
        flowInfo.setFlowType("合同");
        return flowInfo;
    }

    /**
     * 初始化Client
     *
     * @return EssbasicClient
     */
    public static EssbasicClient initClient(){
        // 实例化一个认证对象，入参需要传入腾讯云账户SecretId，SecretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(Config.SecretId, Config.SecretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(Config.EndPoint);
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        EssbasicClient client = new EssbasicClient(cred, "", clientProfile);
        return client;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param filePath  文件路径
     * @return FlowInfo
     */
    public static String convertImageFileToBase64(String filePath) {
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        URL url = CreateFlowUtils.class.getClassLoader().getResource(filePath);
        try {
            assert url != null;
            inputStream = new FileInputStream(url.getFile());
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(buffer);
    }
}
