package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsResponse;

public class DescribeResourceUrlsByFlows {

    public static DescribeResourceUrlsByFlowsResponse describeResourceUrlsByFlows(Agent agent, String[] flowIds) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeResourceUrlsByFlowsRequest req = new DescribeResourceUrlsByFlowsRequest();

            req.setAgent(agent);

            req.setFlowIds(flowIds);

            // 返回的resp是一个DescribeResourceUrlsByFlowsResponse的实例，与请求对象对应
            return client.DescribeResourceUrlsByFlows(req);
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

            String[] flowIds = new String[]{"***************"};

            DescribeResourceUrlsByFlowsResponse urlResp = DescribeResourceUrlsByFlows.
                    describeResourceUrlsByFlows(agent, flowIds);

            assert urlResp != null;
            System.out.println(DescribeResourceUrlsByFlowsResponse.toJsonString(urlResp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}