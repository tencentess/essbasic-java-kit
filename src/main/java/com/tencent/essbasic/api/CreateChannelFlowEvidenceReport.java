package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateChannelFlowEvidenceReportRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateChannelFlowEvidenceReportResponse;

/**
 * 创建出证报告，返回报告 ID
 * 详细参考 https://cloud.tencent.com/document/api/1420/79688
 */

public class CreateChannelFlowEvidenceReport {
    /**
     * 创建并返回出证报告
     *
     * @param agent  第三方平台应用相关信息
     * @param flowId 签署流程编号
     * @return CreateChannelFlowEvidenceReportResponse
     */
    public static CreateChannelFlowEvidenceReportResponse createChannelFlowEvidenceReport(Agent agent, String flowId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateChannelFlowEvidenceReportRequest req = new CreateChannelFlowEvidenceReportRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填
            req.setAgent(agent);
            // 签署流程编号，合同id
            req.setFlowId(flowId);

            // 返回的resp是一个CreateChannelFlowEvidenceReportResponse的实例，与请求对象对应
            return client.CreateChannelFlowEvidenceReport(req);
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
            CreateChannelFlowEvidenceReportResponse loginUrlResponse = CreateChannelFlowEvidenceReport.
                    createChannelFlowEvidenceReport(agent, flowId);
            assert loginUrlResponse != null;
            System.out.println(CreateChannelFlowEvidenceReportResponse.toJsonString(loginUrlResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}