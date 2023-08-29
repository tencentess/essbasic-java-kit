package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeFlowDetailInfoRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeFlowDetailInfoResponse;

public class DescribeFlowDetailInfo {

    public static DescribeFlowDetailInfoResponse describeFlowDetailInfo(Agent agent, String[] flowIds) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFlowDetailInfoRequest req = new DescribeFlowDetailInfoRequest();

            req.setAgent(agent);

            req.setFlowIds(flowIds);

            // 返回的resp是一个DescribeFlowDetailInfoResponse的实例，与请求对象对应
            return client.DescribeFlowDetailInfo(req);
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

            DescribeFlowDetailInfoResponse resp = describeFlowDetailInfo(agent, flowIds);

            // 输出json格式的字符串回包
            assert resp != null;
            System.out.println(DescribeFlowDetailInfoResponse.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}