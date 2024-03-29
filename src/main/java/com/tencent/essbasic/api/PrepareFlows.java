package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

public class PrepareFlows {

    public static PrepareFlowsResponse prepareFlows(Agent agent, String jumpUrl, FlowInfo[] flowInfos) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            PrepareFlowsRequest req = new PrepareFlowsRequest();

            req.setAgent(agent);

            req.setJumpUrl(jumpUrl);

            req.setFlowInfos(flowInfos);

            // 返回的resp是一个PrepareFlowsResponse的实例，与请求对象对应
            return client.PrepareFlows(req);
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

            String jumpUrl = "*********************";

            FlowInfo flowInfo = new FlowInfo();
            flowInfo.setFlowName("合同名称");
            flowInfo.setDeadline(0L);
            flowInfo.setTemplateId("*********************");

            // 签署人信息
            FlowApproverInfo flowApprover = new FlowApproverInfo();
            flowApprover.setName("*********************");
            flowApprover.setIdCardType("");
            flowApprover.setIdCardNumber("");
            flowApprover.setMobile("*********************");
            flowApprover.setOrganizationName("");
            flowApprover.setNotChannelOrganization(false);
            flowApprover.setOpenId("");
            flowApprover.setOrganizationOpenId("");
            flowApprover.setApproverType("");
            flowApprover.setRecipientId("");
            flowApprover.setDeadline(0L);
            flowApprover.setSignComponents(null);
            flowApprover.setComponentLimitType(null);
            flowApprover.setPreReadTime(0L);
            flowApprover.setJumpUrl("");
            flowApprover.setApproverOption(new ApproverOption());
            flowApprover.setApproverNeedSignReview(false);

            FlowApproverInfo[] flowApprovers = {flowApprover};
            flowInfo.setFlowApprovers(flowApprovers);
            
            FormField formField = new FormField();
            formField.setComponentValue("*********************");
            formField.setComponentId("");
            formField.setComponentName("*********************");

            FormField[] formFields = {formField};
            flowInfo.setFormFields(formFields);
            flowInfo.setCallbackUrl("");
            flowInfo.setFlowType("");
            flowInfo.setFlowDescription("");
            flowInfo.setCustomerData("");
            flowInfo.setCustomShowMap("");
            flowInfo.setNeedSignReview(false);

            FlowInfo[] flowInfos = {flowInfo};

            PrepareFlowsResponse PrepareFlowsRes = PrepareFlows.prepareFlows(agent, jumpUrl, flowInfos);
            assert PrepareFlowsRes != null;
            System.out.println(PrepareFlowsResponse.toJsonString(PrepareFlowsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}