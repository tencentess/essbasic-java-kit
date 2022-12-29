package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeChannelFlowEvidenceReportRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeChannelFlowEvidenceReportResponse;

/**
 * 查询出证报告，返回报告 URL。
 * 详细参考 https://cloud.tencent.com/document/api/1420/83442
 */

public class DescribeChannelFlowEvidenceReport {
    /**
     * 查询出证报告
     *
     * @param agent    渠道应用相关信息
     * @param reportId 出证报告编号
     * @return DescribeChannelFlowEvidenceReportResponse
     */
    public static DescribeChannelFlowEvidenceReportResponse describeChannelFlowEvidenceReport(Agent agent, String reportId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeChannelFlowEvidenceReportRequest req = new DescribeChannelFlowEvidenceReportRequest();

            // 渠道应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填
            req.setAgent(agent);
            // 出证报告编号
            req.setReportId(reportId);

            // 返回的resp是一个DescribeChannelFlowEvidenceReportResponse的实例，与请求对象对应
            return client.DescribeChannelFlowEvidenceReport(req);
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
            String reportId = "*********************";
            DescribeChannelFlowEvidenceReportResponse loginUrlResponse = DescribeChannelFlowEvidenceReport.
                    describeChannelFlowEvidenceReport(agent, reportId);
            assert loginUrlResponse != null;
            System.out.println(DescribeChannelFlowEvidenceReportResponse.toJsonString(loginUrlResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}