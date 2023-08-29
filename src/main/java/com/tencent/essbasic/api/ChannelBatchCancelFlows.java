package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsResponse;

public class ChannelBatchCancelFlows {

    public static ChannelBatchCancelFlowsResponse channelBatchCancelFlows(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelBatchCancelFlowsRequest req = new ChannelBatchCancelFlowsRequest();

            req.setAgent(agent);

            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelBatchCancelFlowsResponse的实例，与请求对象对应
            return client.ChannelBatchCancelFlows(req);
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

            String[] flowIds = new String[]{"*********************"};
            ChannelBatchCancelFlowsResponse channelBatchCancelFlowsRes = ChannelBatchCancelFlows.channelBatchCancelFlows(flowIds, agent);
            assert channelBatchCancelFlowsRes != null;
            System.out.println(ChannelBatchCancelFlowsResponse.toJsonString(channelBatchCancelFlowsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}