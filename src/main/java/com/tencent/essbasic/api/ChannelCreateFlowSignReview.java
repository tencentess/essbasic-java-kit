package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateFlowSignReviewRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateFlowSignReviewResponse;

public class ChannelCreateFlowSignReview {

    public static ChannelCreateFlowSignReviewResponse channelCreateFlowSignReview(String flowId, String ReviewType, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowSignReviewRequest req = new ChannelCreateFlowSignReviewRequest();

            req.setAgent(agent);

            req.setFlowId(flowId);

            req.setReviewType(ReviewType);

            // 返回的resp是一个ChannelCreateFlowSignReviewResponse的实例，与请求对象对应
            return client.ChannelCreateFlowSignReview(req);
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

            String flowId = "*********************";
            String ReviewType = "PASS";
            ChannelCreateFlowSignReviewResponse channelCreateFlowSignReviewRes = ChannelCreateFlowSignReview.channelCreateFlowSignReview(flowId, ReviewType, agent);
            assert channelCreateFlowSignReviewRes != null;
            System.out.println(ChannelCreateFlowSignReviewResponse.toJsonString(channelCreateFlowSignReviewRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}