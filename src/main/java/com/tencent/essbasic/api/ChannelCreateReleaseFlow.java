package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 第三方应用集成发起解除协议，主要应用场景为：基于一份已经签署的合同，进行解除操作。
 * 合同发起人必须在电子签已经进行实名。
 * 详细参考 https://cloud.tencent.com/document/api/1420/83461
 */
public class ChannelCreateReleaseFlow {
    /**
     * 第三方应用集成发起解除协议，主要应用场景为：基于一份已经签署的合同，进行解除操作。
     * 合同发起人必须在电子签已经进行实名。
     *
     * @param agent   第三方平台应用相关信息
     * @param flowIds 签署流程Id数组，最多100个，超过100不处理
     * @return ChannelCreateReleaseFlowResponse
     */
    public static ChannelCreateReleaseFlowResponse channelCreateReleaseFlow(Agent agent,
                                                                            String needRelievedFlowId,
                                                                            RelieveInfo reliveInfo,
                                                                            ReleasedApprover[] releasedApprovers,
                                                                            String callbackUrl) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateReleaseFlowRequest req = new ChannelCreateReleaseFlowRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 待解除的流程编号（即原流程的编号）
            req.setNeedRelievedFlowId(needRelievedFlowId);
            // 解除协议内容，其中Reason必填
            // 详细参考 https://cloud.tencent.com/document/api/1420/61525#RelieveInfo
            req.setReliveInfo(reliveInfo);
            // 非必须，解除协议的本企业签署人列表，默认使用原流程的签署人列表；
            // 当解除协议的签署人与原流程的签署人不能相同时（例如原流程签署人离职了），需要指定本企业的其他签署人来替换原流程中的原签署人，
            // 注意需要指明ApproverNumber来代表需要替换哪一个签署人，解除协议的签署人数量不能多于原流程的签署人数量
            req.setReleasedApprovers(releasedApprovers);
            // 签署完回调url，最大长度1000个字符
            req.setCallbackUrl(callbackUrl);

            // 返回的resp是一个ChannelCreateReleaseFlowResponse的实例，与请求对象对应
            return client.ChannelCreateReleaseFlow(req);
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
            String needRelievedFlowId = "*********************";
            RelieveInfo reliveInfo = new RelieveInfo();
            reliveInfo.setReason("*********************");
            reliveInfo.setRemainInForceItem("*********************");
            reliveInfo.setOriginalExpenseSettlement("*********************");
            reliveInfo.setOriginalOtherSettlement("*********************");
            reliveInfo.setOtherDeals("*********************");

            ReleasedApprover releasedApprover = new ReleasedApprover();
            releasedApprover.setOrganizationName("*********************");
            releasedApprover.setApproverNumber(0L);
            releasedApprover.setApproverType("*********************");
            releasedApprover.setName("*********************");
            releasedApprover.setIdCardType("*********************");
            releasedApprover.setIdCardNumber("*********************");
            releasedApprover.setMobile("*********************");
            releasedApprover.setOrganizationOpenId("*********************");
            releasedApprover.setOpenId("*********************");
            
            ReleasedApprover[] releasedApprovers = new ReleasedApprover[]{releasedApprover};
            String callbackUrl = "*********************";
            ChannelCreateReleaseFlowResponse ChannelCreateReleaseFlowRes = ChannelCreateReleaseFlow.
                    channelCreateReleaseFlow(agent, needRelievedFlowId, reliveInfo, releasedApprovers, callbackUrl);
            assert ChannelCreateReleaseFlowRes != null;
            System.out.println(ChannelCreateReleaseFlowResponse.toJsonString(ChannelCreateReleaseFlowRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}