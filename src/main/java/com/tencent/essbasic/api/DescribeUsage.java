package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeUsageRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeUsageResponse;

/**
 * 此接口（DescribeUsage）用于获取渠道所有合作企业流量消耗情况。
 * 注: 此接口每日限频2次，若要扩大限制次数,请提前与客服经理或邮件至e-contract@tencent.com进行联系。
 * 详细参考 https://cloud.tencent.com/document/api/1420/61520
 */
public class DescribeUsage {
    /**
     * 获取渠道所有合作企业流量消耗情况
     *
     * @param agent     渠道应用相关信息
     * @param StartDate 资源所对应的签署流程Id
     * @param EndDate   资源所对应的签署流程Id
     * @return DescribeUsageResponse
     */
    public static DescribeUsageResponse describeUsage(String StartDate, String EndDate, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeUsageRequest req = new DescribeUsageRequest();

            // 应用信息，此接口Agent.AppId必填
            req.setAgent(agent);
            // 开始时间，例如：2021-03-21
            req.setStartDate(StartDate);
            // 结束时间，例如：2021-06-21；
            // 开始时间到结束时间的区间长度小于等于90天。
            req.setEndDate(EndDate);

            // 返回的resp是一个DescribeUsageResponse的实例，与请求对象对应
            return client.DescribeUsage(req);
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
            String startDate = "2021-03-21";
            String endDate = "2021-06-21";
            DescribeUsageResponse describeUsageRes = DescribeUsage.describeUsage(startDate, endDate, agent);
            assert describeUsageRes != null;
            System.out.println(DescribeUsageResponse.toJsonString(describeUsageRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}