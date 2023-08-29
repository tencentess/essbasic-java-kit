package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelFlowRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelFlowResponse;

public class ChannelCancelFlow {

    public static ChannelCancelFlowResponse channelCancelFlow(Agent agent, String flowId, String cancelMessage,
                                                              Long cancelMessageFormat) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCancelFlowRequest req = new ChannelCancelFlowRequest();

            req.setAgent(agent);

            req.setFlowId(flowId);

            req.setCancelMessage(cancelMessage);

            req.setCancelMessageFormat(cancelMessageFormat);

            // 返回的resp是一个ChannelCancelFlowResponse的实例，与请求对象对应
            return client.ChannelCancelFlow(req);
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

            String flowId = "*********************";
            String cancelMessage = "*********************";
            Long cancelMessageFormat = 1L;

            ChannelCancelFlowResponse ChannelCancelFlowRes = ChannelCancelFlow.channelCancelFlow(agent, flowId,
                    cancelMessage, cancelMessageFormat);
            assert ChannelCancelFlowRes != null;
            System.out.println(ChannelCancelFlowResponse.toJsonString(ChannelCancelFlowRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}