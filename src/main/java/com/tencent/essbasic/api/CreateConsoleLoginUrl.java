package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 用于创建电子签控制台登录链接。若企业未激活，调用同步企业信息、同步经办人信息
 */

public class CreateConsoleLoginUrl {
    /**
     * 创建电子签控制台登录链接
     *
     * @param agent                 渠道应用相关信息
     * @param proxyOrganizationName 渠道侧合作企业名称
     * @return CreateConsoleLoginUrlResponse
     */
    public static CreateConsoleLoginUrlResponse createConsoleLoginUrl(Agent agent, String proxyOrganizationName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateConsoleLoginUrlRequest req = new CreateConsoleLoginUrlRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 渠道侧合作企业名称，最大长度64个字符
            req.setProxyOrganizationName(proxyOrganizationName);

            // 返回的resp是一个CreateConsoleLoginUrlResponse的实例，与请求对象对应
            CreateConsoleLoginUrlResponse resp = client.CreateConsoleLoginUrl(req);
            return resp;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            Agent agent = CreateFlowUtils.setAgent();
            String proxyOrganizationName = "好企业";
            CreateConsoleLoginUrlResponse loginUrlResponse = CreateConsoleLoginUrl.createConsoleLoginUrl(agent, proxyOrganizationName);
            assert loginUrlResponse != null;
            System.out.println(CreateConsoleLoginUrlResponse.toJsonString(loginUrlResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}