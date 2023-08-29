package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateConvertTaskApiRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateConvertTaskApiResponse;

public class ChannelCreateConvertTaskApi {

    public static ChannelCreateConvertTaskApiResponse channelCreateConvertTaskApi(String ResourceType, String ResourceName, String ResourceId, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateConvertTaskApiRequest req = new ChannelCreateConvertTaskApiRequest();

            req.setAgent(agent);

            req.setResourceType(ResourceType);

            req.setResourceName(ResourceName);

            req.setResourceId(ResourceId);

            // 返回的resp是一个ChannelCreateConvertTaskApiResponse的实例，与请求对象对应
            return client.ChannelCreateConvertTaskApi(req);
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

            String ResourceType = "docx";
            String ResourceName = "hello.docx";

            String ResourceId = "********************************";

            ChannelCreateConvertTaskApiResponse channelCreateConvertTaskApiRes = ChannelCreateConvertTaskApi.channelCreateConvertTaskApi(ResourceType, ResourceName, ResourceId, agent);
            assert channelCreateConvertTaskApiRes != null;
            System.out.println(ChannelCreateConvertTaskApiResponse.toJsonString(channelCreateConvertTaskApiRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}