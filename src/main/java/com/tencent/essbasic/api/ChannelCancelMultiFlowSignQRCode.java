package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelMultiFlowSignQRCodeResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

public class ChannelCancelMultiFlowSignQRCode {

    public static ChannelCancelMultiFlowSignQRCodeResponse channelCancelMultiFlowSignQRCode
    (Agent agent, String qrCodeId) {
        try {
            // 实例化一个client。
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCancelMultiFlowSignQRCodeRequest req = new ChannelCancelMultiFlowSignQRCodeRequest();

            req.setAgent(agent);

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

            Agent agent = setAgent();

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