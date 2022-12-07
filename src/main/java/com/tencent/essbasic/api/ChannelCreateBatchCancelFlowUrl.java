package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBatchCancelFlowUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateBatchCancelFlowUrlResponse;

/**
 * 指定需要批量撤销的签署流程Id，获取批量撤销链接
 * 客户指定需要撤销的签署流程Id，最多100个，超过100不处理；接口调用成功返回批量撤销合同的链接，通过链接跳转到电子签小程序完成批量撤销
 */
public class ChannelCreateBatchCancelFlowUrl {
    /**
     * 创建跳转小程序查看或签署的链接
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return ChannelCreateBatchCancelFlowUrlResponse
     */
    public static ChannelCreateBatchCancelFlowUrlResponse channelCreateBatchCancelFlowUrl(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateBatchCancelFlowUrlRequest req = new ChannelCreateBatchCancelFlowUrlRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
            req.setFlowIds(flowIds);

            // 返回的resp是一个ChannelCreateBatchCancelFlowUrlResponse的实例，与请求对象对应
            return client.ChannelCreateBatchCancelFlowUrl(req);
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
            ChannelCreateBatchCancelFlowUrlResponse channelCreateBatchCancelFlowUrlRes = ChannelCreateBatchCancelFlowUrl.channelCreateBatchCancelFlowUrl(flowIds, agent);
            assert channelCreateBatchCancelFlowUrlRes != null;
            System.out.println(ChannelCreateBatchCancelFlowUrlResponse.toJsonString(channelCreateBatchCancelFlowUrlRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}