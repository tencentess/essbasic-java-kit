package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBoundFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBoundFlowsResponse;

/**
 * 用于子客领取合同，经办人需要有相应的角色，领取后的合同不能重复领取。
 * 详细参考 https://cloud.tencent.com/document/api/1420/83118
 */
public class ChannelCreateBoundFlows {
    /**
     * 此接口（ChannelCreateBoundFlows）用于子客领取合同，经办人需要有相应的角色，领取后的合同不能重复领取。
     *
     * @param agent   第三方平台应用相关信息
     * @param flowIds 领取的合同id列表
     * @return ChannelCreateBoundFlowsResponse
     */
    public static ChannelCreateBoundFlowsResponse channelCreateBoundFlows(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateBoundFlowsRequest req = new ChannelCreateBoundFlowsRequest();

            // 应用信息
	        // 此接口Agent.AppId、Agent.ProxyOrganizationOpenId 和 Agent. ProxyOperator.OpenId 必填
            req.setAgent(agent);
            // 领取的合同id列表
            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelCreateBoundFlowsResponse的实例，与请求对象对应
            return client.ChannelCreateBoundFlows(req);
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
            // 领取的合同id列表
            String[] flowIds = new String[]{"*********************"};
            ChannelCreateBoundFlowsResponse ChannelCreateBoundFlowsRes = ChannelCreateBoundFlows.
                    channelCreateBoundFlows(flowIds, agent);
            assert ChannelCreateBoundFlowsRes != null;
            System.out.println(ChannelCreateBoundFlowsResponse.toJsonString(ChannelCreateBoundFlowsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}