package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBoundFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBoundFlowsResponse;

public class ChannelCreateBoundFlows {

    public static ChannelCreateBoundFlowsResponse channelCreateBoundFlows(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateBoundFlowsRequest req = new ChannelCreateBoundFlowsRequest();

            req.setAgent(agent);

            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelCreateBoundFlowsResponse的实例，与请求对象对应
            return client.ChannelCreateBoundFlows(req);
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

            String[] flowIds = new String[]{"*********************"};
            ChannelCreateBoundFlowsResponse ChannelCreateBoundFlowsRes = ChannelCreateBoundFlows.
                    channelCreateBoundFlows(flowIds, agent);
            assert ChannelCreateBoundFlowsRes != null;
            System.out.println(ChannelCreateBoundFlowsResponse.toJsonString(ChannelCreateBoundFlowsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}