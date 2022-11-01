package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 指定需要批量撤销的签署流程Id，批量撤销合同
 * 客户指定需要撤销的签署流程Id，最多100个，超过100不处理；接口失败后返回错误信息
 */
public class ChannelBatchCancelFlows {
    /**
     * 创建跳转小程序查看或签署的链接
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 签署流程Id数组，最多100个，超过100不处理
     * @return ChannelBatchCancelFlowsResponse
     */
    public static ChannelBatchCancelFlowsResponse channelBatchCancelFlows(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelBatchCancelFlowsRequest req = new ChannelBatchCancelFlowsRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelBatchCancelFlowsResponse的实例，与请求对象对应
            ChannelBatchCancelFlowsResponse resp = client.ChannelBatchCancelFlows(req);
            return resp;
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