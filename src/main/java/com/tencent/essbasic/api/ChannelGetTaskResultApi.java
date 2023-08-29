package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelGetTaskResultApiRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelGetTaskResultApiResponse;

public class ChannelGetTaskResultApi {

    public static ChannelGetTaskResultApiResponse channelGetTaskResultApi(String TaskId, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelGetTaskResultApiRequest req = new ChannelGetTaskResultApiRequest();

            req.setAgent(agent);

            req.setTaskId(TaskId);

            // 返回的resp是一个ChannelGetTaskResultApiResponse的实例，与请求对象对应
            return client.ChannelGetTaskResultApi(req);
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

            String TaskId = "*********************";
            ChannelGetTaskResultApiResponse channelGetTaskResultApiRes = ChannelGetTaskResultApi.channelGetTaskResultApi(TaskId, agent);
            assert channelGetTaskResultApiRes != null;
            System.out.println(ChannelGetTaskResultApiResponse.toJsonString(channelGetTaskResultApiRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}