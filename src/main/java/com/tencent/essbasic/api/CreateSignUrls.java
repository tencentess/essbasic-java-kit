package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSignUrlsResponse;

/**
 * 创建跳转小程序查看或签署的链接。
 * 详细参考 https://cloud.tencent.com/document/api/1420/61522
 */

/*
跳转小程序的几种方式：主要是设置不同的EndPoint

通过链接Url直接跳转到小程序，不需要返回
设置EndPoint为WEIXINAPP，得到链接打开即可。（与短信提醒用户签署形式一样）。
通过链接Url打开H5引导页-->点击跳转到小程序-->签署完退出小程序-->回到H5引导页-->跳转到指定JumpUrl
设置EndPoint为CHANNEL，指定JumpUrl，得到链接打开即可。
客户App直接跳转到小程序-->小程序签署完成-->返回App
跳转到小程序的实现，参考官方文档（分为全屏、半屏两种方式）
全屏方式：
（https://developers.weixin.qq.com/miniprogram/dev/api/navigate/wx.navigateToMiniProgram.html）
半屏方式：
（https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/openEmbeddedMiniProgram.html）
其中小程序的原始Id，请联系<对接技术人员>获取，或者查看小程序信息自助获取。
使用CreateSignUrls，设置EndPoint为APP，得到path。
客户小程序直接跳到电子签小程序-->签署完成退出电子签小程序-->回到客户小程序
实现方式同App跳小程序。
*/
public class CreateSignUrls {
    /**
     * 创建跳转小程序查看或签署的链接
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return CreateSignUrlsResponse
     */
    public static CreateSignUrlsResponse createSignUrls(String[] flowIds, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSignUrlsRequest req = new CreateSignUrlsRequest();

            // 渠道应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 签署流程编号数组，最多支持100个
            req.setFlowIds(flowIds);

            // 返回的resp是一个CreateSignUrlsResponse的实例，与请求对象对应
            return client.CreateSignUrls(req);
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
            CreateSignUrlsResponse createSignUrlsRes = CreateSignUrls.createSignUrls(flowIds, agent);
            assert createSignUrlsRes != null;
            System.out.println(CreateSignUrlsResponse.toJsonString(createSignUrlsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}