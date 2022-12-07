package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateFlowSignReviewRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateFlowSignReviewResponse;

/**
 * 提交企业签署流程审批结果
 * 在通过接口(CreateFlowsByTemplates 或者ChannelCreateFlowByFiles)创建签署流程时，若指定了参数 NeedSignReview 为true,则可以调用此接口提交企业内部签署审批结果。
 * 若签署流程状态正常，且本企业存在签署方未签署，同一签署流程可以多次提交签署审批结果，签署时的最后一个“审批结果”有效。
 */
public class ChannelCreateFlowSignReview {
    /**
     * 提交企业签署流程审批结果
     *
     * @param agent      渠道应用相关信息
     * @param flowId     资源所对应的签署流程Id
     * @param ReviewType 企业内部审核结果  PASS: 通过，REJECT: 拒绝
     * @return ChannelCreateFlowSignReviewResponse
     */
    public static ChannelCreateFlowSignReviewResponse channelCreateFlowSignReview(String flowId, String ReviewType, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowSignReviewRequest req = new ChannelCreateFlowSignReviewRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
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
            // 设置agent参数
            Agent agent = CreateFlowUtils.setAgent();
            // 发起合同成功的签署流程Id
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