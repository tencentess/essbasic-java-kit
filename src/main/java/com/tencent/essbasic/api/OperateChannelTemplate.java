package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 此接口（OperateChannelTemplate）用于渠道侧将模板库中的模板对合作企业进行查询和设置, 其中包括可见性的修改以及对合作企业的设置
 */
public class OperateChannelTemplate {
    /**
     * 
     *
     * @param agent      渠道应用相关信息
     * @param TemplateId 模板唯一标识
     * @return OperateChannelTemplateResponse
     */
    public static OperateChannelTemplateResponse operateChannelTemplate(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            OperateChannelTemplateRequest req = new OperateChannelTemplateRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 模板唯一标识
            req.setTemplateId(TemplateId);
            // 操作类型，查询:"SELECT"，删除:"DELETE"，更新:"UPDATE"
            req.setOperateType("SELECT");

            // 返回的resp是一个OperateChannelTemplateResponse的实例，与请求对象对应
            OperateChannelTemplateResponse resp = client.OperateChannelTemplate(req);
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