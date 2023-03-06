package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelMultiFlowSignQRCodeResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 用于取消一码多扫二维码。该接口对传入的二维码ID，若还在有效期内，可以提前失效。
 */
public class ChannelCancelMultiFlowSignQRCode {
    /**
     * 取消一码多扫二维码
     * 用于取消一码多扫二维码。该接口对传入的二维码ID，若还在有效期内，可以提前失效
     * 详细参考 https://cloud.tencent.com/document/api/1420/75453
     *
     * @param agent    第三方平台应用相关信息
     * @param qrCodeId 二维码ID
     * @return ChannelCancelMultiFlowSignQRCodeResponse
     */
    public static ChannelCancelMultiFlowSignQRCodeResponse channelCancelMultiFlowSignQRCode
    (Agent agent, String qrCodeId) {
        try {
            // 实例化一个client。
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCancelMultiFlowSignQRCodeRequest req = new ChannelCancelMultiFlowSignQRCodeRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 二维码ID
            req.setQrCodeId(qrCodeId);

            // 返回的resp是一个ChannelCancelMultiFlowSignQRCodeResponse的实例，与请求对象对应
            return client.ChannelCancelMultiFlowSignQRCode(req);
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
            // agent参数
            Agent agent = setAgent();
            // 二维码Id
            String qrCodeId = "******************";

            ChannelCancelMultiFlowSignQRCodeResponse resp = channelCancelMultiFlowSignQRCode(agent, qrCodeId);
            // 输出json格式的字符串回包
            assert resp != null;
            System.out.println(ChannelCancelMultiFlowSignQRCodeResponse.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}