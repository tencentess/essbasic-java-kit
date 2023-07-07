package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.OperateChannelTemplateRequest;
import com.tencentcloudapi.essbasic.v20210526.models.OperateChannelTemplateResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 此用于针对平台企业模板库中的模板对子客企业可见性的查询和设置，不会直接分配平台企业模板给子客企业。
 * 1、OperateType=select时：
 * 查询平台企业模板库
 * 2、OperateType=update或者delete时：
 * 对子客企业进行模板库中模板可见性的修改、删除操作。
 * 详细参考 https://cloud.tencent.com/document/api/1420/66367
 */
public class OperateChannelTemplate {
    /**
     * @param agent      第三方平台应用相关信息
     * @param TemplateId 模板唯一标识
     * @return OperateChannelTemplateResponse
     */
    public static OperateChannelTemplateResponse operateChannelTemplate(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            OperateChannelTemplateRequest req = new OperateChannelTemplateRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 必填。
            req.setAgent(agent);
            // 平台企业模板库模板唯一标识
            req.setTemplateId(TemplateId);
            // 操作类型，查询:"SELECT"，删除:"DELETE"，更新:"UPDATE"
            req.setOperateType("SELECT");

            // 返回的resp是一个OperateChannelTemplateResponse的实例，与请求对象对应
            return client.OperateChannelTemplate(req);
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
            String TemplateId = "***************";
            // 设置agent参数
            Agent agent = setAgent();

            OperateChannelTemplateResponse templatesResponse = OperateChannelTemplate.operateChannelTemplate(agent, TemplateId);
            assert templatesResponse != null;
            System.out.println(OperateChannelTemplateResponse.toJsonString(templatesResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}