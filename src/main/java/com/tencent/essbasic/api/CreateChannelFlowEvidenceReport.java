package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateChannelFlowEvidenceReportRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateChannelFlowEvidenceReportResponse;

public class CreateChannelFlowEvidenceReport {
    public static CreateChannelFlowEvidenceReportResponse createChannelFlowEvidenceReport(Agent agent, String flowId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateChannelFlowEvidenceReportRequest req = new CreateChannelFlowEvidenceReportRequest();

            req.setAgent(agent);

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