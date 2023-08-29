package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsResponse;

public class ChannelDescribeOrganizationSeals {

    public static ChannelDescribeOrganizationSealsResponse channelDescribeOrganizationSeals(Agent agent,
                                                                                            String sealId,
                                                                                            Long infoType,
                                                                                            Long offset,
                                                                                            Long limit) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelDescribeOrganizationSealsRequest req = new ChannelDescribeOrganizationSealsRequest();

            req.setAgent(agent);

            req.setSealId(sealId);

            req.setInfoType(infoType);

            req.setLimit(limit);

            req.setOffset(offset);

            // 返回的resp是一个ChannelDescribeOrganizationSealsResponse的实例，与请求对象对应
            return client.ChannelDescribeOrganizationSeals(req);
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

            String sealId = "*********************";
            Long infoType = 1L;
            Long limit = 10L;
            Long offset = 0L;

            ChannelDescribeOrganizationSealsResponse ChannelDescribeOrganizationSealsRes =
                    ChannelDescribeOrganizationSeals.channelDescribeOrganizationSeals(agent, sealId, infoType, limit, offset);
            assert ChannelDescribeOrganizationSealsRes != null;
            System.out.println(ChannelDescribeOrganizationSealsResponse.toJsonString(ChannelDescribeOrganizationSealsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}