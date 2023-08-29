package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

public class ChannelCreateReleaseFlow {

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

            req.setAgent(agent);

            req.setNeedRelievedFlowId(needRelievedFlowId);

            req.setReliveInfo(reliveInfo);

            req.setReleasedApprovers(releasedApprovers);

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