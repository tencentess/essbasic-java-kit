package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBatchCancelFlowUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBatchCancelFlowUrlResponse;

public class ChannelCreateBatchCancelFlowUrl {

    public static ChannelCreateBatchCancelFlowUrlResponse channelCreateBatchCancelFlowUrl(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateBatchCancelFlowUrlRequest req = new ChannelCreateBatchCancelFlowUrlRequest();

            req.setAgent(agent);

            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelCreateBatchCancelFlowUrlResponse的实例，与请求对象对应
            return client.ChannelCreateBatchCancelFlowUrl(req);
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
            ChannelCreateBatchCancelFlowUrlResponse channelCreateBatchCancelFlowUrlRes = ChannelCreateBatchCancelFlowUrl.channelCreateBatchCancelFlowUrl(flowIds, agent);
            assert channelCreateBatchCancelFlowUrlRes != null;
            System.out.println(ChannelCreateBatchCancelFlowUrlResponse.toJsonString(channelCreateBatchCancelFlowUrlRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}