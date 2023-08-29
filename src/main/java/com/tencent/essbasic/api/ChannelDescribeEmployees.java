package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeEmployeesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeEmployeesResponse;
import com.tencentcloudapi.essbasic.v20210526.models.Filter;

public class ChannelDescribeEmployees {

    public static ChannelDescribeEmployeesResponse channelDescribeEmployees(Agent agent,
                                                                            Filter[] filters,
                                                                            Long limit,
                                                                            Long offset) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelDescribeEmployeesRequest req = new ChannelDescribeEmployeesRequest();

            req.setAgent(agent);

            req.setFilters(filters);

            req.setLimit(limit);

            req.setOffset(offset);

            // 返回的resp是一个ChannelDescribeEmployeesResponse的实例，与请求对象对应
            return client.ChannelDescribeEmployees(req);
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

            Filter filter = new Filter();
            filter.setKey("Status");
            String[] values = {"IsVerified"};
            filter.setValues(values);

            Filter[] filters = {filter};
            Long limit = 10L;
            Long offset = 0L;

            ChannelDescribeEmployeesResponse ChannelDescribeEmployeesRes = ChannelDescribeEmployees.
                    channelDescribeEmployees(agent, filters, limit, offset);
            assert ChannelDescribeEmployeesRes != null;
            System.out.println(ChannelDescribeEmployeesResponse.toJsonString(ChannelDescribeEmployeesRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}