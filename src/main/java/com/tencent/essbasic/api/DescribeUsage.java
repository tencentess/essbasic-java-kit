package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeUsageRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeUsageResponse;

public class DescribeUsage {

    public static DescribeUsageResponse describeUsage(String StartDate, String EndDate, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeUsageRequest req = new DescribeUsageRequest();

            req.setAgent(agent);

            req.setStartDate(StartDate);

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