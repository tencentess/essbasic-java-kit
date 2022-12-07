package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsResponse;

/**
 * 创建跳转小程序查看或签署的链接；自动签署的签署方不创建签署链接；
 */
public class CreateSignUrls {
    /**
     * 创建跳转小程序查看或签署的链接
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return CreateSignUrlsResponse
     */
    public static CreateSignUrlsResponse createSignUrls(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSignUrlsRequest req = new CreateSignUrlsRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
            req.setFlowIds(flowIds);

            // 返回的resp是一个CreateSignUrlsResponse的实例，与请求对象对应
            return client.CreateSignUrls(req);
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
            // 设置agent参数
            Agent agent = CreateFlowUtils.setAgent();
            // 发起合同成功的签署流程Id
            String[] flowIds = new String[]{"*********************"};
            CreateSignUrlsResponse createSignUrlsRes = CreateSignUrls.createSignUrls(flowIds, agent);
            assert createSignUrlsRes != null;
            System.out.println(CreateSignUrlsResponse.toJsonString(createSignUrlsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}