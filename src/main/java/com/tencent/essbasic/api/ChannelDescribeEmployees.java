package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeEmployeesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeEmployeesResponse;
import com.tencentcloudapi.essbasic.v20210526.models.Filter;

/**
 * 查询企业员工
 */
public class ChannelDescribeEmployees {
    /**
     * 查询企业员工列表
     *
     * @param agent   渠道应用相关信息
     * @param filters 查询过滤实名用户
     * @param limit   返回最大数量，最大为20
     * @param offset  偏移量，默认为0，最大为20000
     * @return ChannelDescribeEmployeesResponse
     */
    public static ChannelDescribeEmployeesResponse channelDescribeEmployees(Agent agent,
                                                                            Filter[] filters,
                                                                            Long limit,
                                                                            Long offset) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelDescribeEmployeesRequest req = new ChannelDescribeEmployeesRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 查询过滤实名用户，Key为Status，Values为["IsVerified"]
            // 根据第三方系统openId过滤查询员工时,Key为StaffOpenId,Values为["OpenId","OpenId",...]
            // 查询离职员工时，Key为Status，Values为["QuiteJob"]
            req.setFilters(filters);
            // 返回最大数量，最大为20
            req.setLimit(limit);
            // 偏移量，默认为0，最大为20000
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
            // 设置agent参数
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