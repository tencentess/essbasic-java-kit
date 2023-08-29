package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateMultiFlowSignQRCodeResponse;

public class ChannelCreateMultiFlowSignQRCode {

    public static ChannelCreateMultiFlowSignQRCodeResponse channelCreateMultiFlowSignQRCode
    (Agent agent, String templateId, String flowName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateMultiFlowSignQRCodeRequest req = new ChannelCreateMultiFlowSignQRCodeRequest();

            req.setAgent(agent);

            req.setTemplateId(templateId);

            req.setFlowName(flowName);

            // 返回的resp是一个ChannelCreateMultiFlowSignQRCodeResponse的实例，与请求对象对应

            return client.ChannelCreateMultiFlowSignQRCode(req);
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

            String templateId = "****************";

            String flowName = "我的第一份合同";

            ChannelCreateMultiFlowSignQRCodeResponse resp = channelCreateMultiFlowSignQRCode(agent, templateId, flowName);

            // 输出json格式的字符串回包
            assert resp != null;
            System.out.println(ChannelCreateMultiFlowSignQRCodeResponse.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}