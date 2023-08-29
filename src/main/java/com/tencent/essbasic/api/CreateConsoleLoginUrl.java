package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateConsoleLoginUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateConsoleLoginUrlResponse;

public class CreateConsoleLoginUrl {

    public static CreateConsoleLoginUrlResponse createConsoleLoginUrl(Agent agent, String proxyOrganizationName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateConsoleLoginUrlRequest req = new CreateConsoleLoginUrlRequest();

            agent.setProxyAppId(""); 
            req.setAgent(agent);

            req.setProxyOrganizationName(proxyOrganizationName);

            // 返回的resp是一个CreateConsoleLoginUrlResponse的实例，与请求对象对应
            return client.CreateConsoleLoginUrl(req);
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