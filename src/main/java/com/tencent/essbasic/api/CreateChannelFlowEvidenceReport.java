package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 创建出证报告，返回报告 URL
 */
public class CreateChannelFlowEvidenceReport {
    /**
     * 创建出证报告，返回报告 URL
     *
     * @param agent   渠道应用相关信息
     * @param FlowId 签署流程Id
     * @return CreateChannelFlowEvidenceReportResponse
     */
    public static CreateChannelFlowEvidenceReportResponse createChannelFlowEvidenceReport(String FlowId, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateChannelFlowEvidenceReportRequest req = new CreateChannelFlowEvidenceReportRequest();

            // 渠道应用相关信息
            req.setAgent(agent);

            req.setFlowId(FlowId);

            // 返回的resp是一个CreateChannelFlowEvidenceReportResponse的实例，与请求对象对应
            CreateChannelFlowEvidenceReportResponse resp = client.CreateChannelFlowEvidenceReport(req);
            return resp;
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
            CreateChannelFlowEvidenceReportResponse createChannelFlowEvidenceReportRes = CreateChannelFlowEvidenceReport.createChannelFlowEvidenceReport(flowId, agent);
            assert createChannelFlowEvidenceReportRes != null;
            System.out.println(CreateChannelFlowEvidenceReportResponse.toJsonString(createChannelFlowEvidenceReportRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}