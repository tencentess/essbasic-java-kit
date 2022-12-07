package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateMultiFlowSignQRCodeRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateMultiFlowSignQRCodeResponse;

/**
 * 用于创建一码多扫流程签署二维码。
 * 适用场景：无需填写签署人信息，可通过模板id生成签署二维码，签署人可通过扫描二维码补充签署信息进行实名签署。常用于提前不知道签署人的身份信息场景，例如：劳务工招工、大批量员工入职等场景。
 * 适用的模板仅限于B2C（1、无序签署，2、顺序签署时B静默签署，3、顺序签署时B非首位签署）、单C的模板，且模板中发起方没有填写控件。
 */

public class ChannelCreateMultiFlowSignQRCode {
    /**
     * 创建一码多扫流程签署二维码
     *
     * @param agent      渠道应用相关信息
     * @param templateId 模板ID
     * @param flowName   签署流程名称
     * @return ChannelCancelMultiFlowSignQRCodeResponse
     */
    public static ChannelCreateMultiFlowSignQRCodeResponse channelCreateMultiFlowSignQRCode
    (Agent agent, String templateId, String flowName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateMultiFlowSignQRCodeRequest req = new ChannelCreateMultiFlowSignQRCodeRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 模板ID
            req.setTemplateId(templateId);
            // 签署流程名称，最大长度200个字符。
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
            // 渠道应用相关信息
            Agent agent = CreateFlowUtils.setAgent();
            // 模板Id
            String templateId = "****************";
            // 签署流程名称，最大长度200个字符。
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