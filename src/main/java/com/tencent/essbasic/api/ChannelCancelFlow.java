package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelFlowRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelFlowResponse;

/**
 * 渠道版撤销签署流程
 */
public class ChannelCancelFlow {
    /**
     * 渠道版撤销签署流程接口
     * 可以撤回：未全部签署完成；不可以撤回（终态）：已全部签署完成、已拒签、已过期、已撤回
     * 注意:
     * 能撤回合同的只能是合同的发起人或者发起企业的超管、法人
     * 详细参考 https://cloud.tencent.com/document/api/1420/81869
     *
     * @param agent               渠道应用相关信息
     * @param flowId              签署流程编号
     * @param cancelMessage       撤回原因，最大不超过200字符
     * @param cancelMessageFormat 撤销理由自定义格式
     * @return ChannelCancelFlowResponse
     */
    public static ChannelCancelFlowResponse channelCancelFlow(Agent agent, String flowId, String cancelMessage,
                                                              Long cancelMessageFormat) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCancelFlowRequest req = new ChannelCancelFlowRequest();

           // 渠道应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 签署流程Id数组，最多100个，超过100不处理
            req.setFlowId(flowId);
            // 撤回原因，最大不超过200字符
            req.setCancelMessage(cancelMessage);
            // 撤销理由自定义格式；选项：
            // 0 默认格式
            // 1 只保留身份信息：展示为【发起方】
            // 2 保留身份信息+企业名称：展示为【发起方xxx公司】
            // 3 保留身份信息+企业名称+经办人名称：展示为【发起方xxxx公司-经办人姓名】
            req.setCancelMessageFormat(cancelMessageFormat);

            // 返回的resp是一个ChannelCancelFlowResponse的实例，与请求对象对应
            return client.ChannelCancelFlow(req);
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

            String flowId = "*********************";
            String cancelMessage = "*********************";
            Long cancelMessageFormat = 1L;

            ChannelCancelFlowResponse ChannelCancelFlowRes = ChannelCancelFlow.channelCancelFlow(agent, flowId,
                    cancelMessage, cancelMessageFormat);
            assert ChannelCancelFlowRes != null;
            System.out.println(ChannelCancelFlowResponse.toJsonString(ChannelCancelFlowRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}