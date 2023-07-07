package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelVerifyPdfRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelVerifyPdfResponse;

/**
 * 合同文件验签
 * 详细参考 https://cloud.tencent.com/document/api/1420/80799
 */
public class ChannelVerifyPdf {
    /**
     * 合同文件验签
     *
     * @param agent  第三方平台应用相关信息
     * @param flowId 合同Id，流程Id
     * @return ChannelVerifyPdfResponse
     */
    public static ChannelVerifyPdfResponse channelVerifyPdf(Agent agent, String flowId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelVerifyPdfRequest req = new ChannelVerifyPdfRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 必填。
            req.setAgent(agent);
            // 合同Id，流程Id
            req.setFlowId(flowId);

            // 返回的resp是一个ChannelVerifyPdfResponse的实例，与请求对象对应
            return client.ChannelVerifyPdf(req);
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

            ChannelVerifyPdfResponse ChannelVerifyPdfRes = ChannelVerifyPdf.channelVerifyPdf(agent, flowId);
            assert ChannelVerifyPdfRes != null;
            System.out.println(ChannelVerifyPdfResponse.toJsonString(ChannelVerifyPdfRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}