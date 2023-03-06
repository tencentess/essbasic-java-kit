package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelBatchCancelFlowsResponse;

/**
 * 电子签第三方应用集成-根据签署流程id批量撤销合同
 */
public class ChannelBatchCancelFlows {
    /**
     * 指定需要批量撤销的签署流程Id，批量撤销合同
     * 客户指定需要撤销的签署流程Id，最多100个，超过100不处理；接口失败后返回错误信息
     * 注意:
     * 能撤回合同的只能是合同的发起人或者发起企业的超管、法人
     * 详细参考 https://cloud.tencent.com/document/api/1420/80391
     *
     * @param agent   第三方平台应用相关信息
     * @param flowIds 签署流程Id数组，最多100个，超过100不处理
     * @return ChannelBatchCancelFlowsResponse
     */
    public static ChannelBatchCancelFlowsResponse channelBatchCancelFlows(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelBatchCancelFlowsRequest req = new ChannelBatchCancelFlowsRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 签署流程Id数组，最多100个，超过100不处理
            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelBatchCancelFlowsResponse的实例，与请求对象对应
            return client.ChannelBatchCancelFlows(req);
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
            String[] flowIds = new String[]{"*********************"};
            ChannelBatchCancelFlowsResponse channelBatchCancelFlowsRes = ChannelBatchCancelFlows.channelBatchCancelFlows(flowIds, agent);
            assert channelBatchCancelFlowsRes != null;
            System.out.println(ChannelBatchCancelFlowsResponse.toJsonString(channelBatchCancelFlowsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}