package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.OperateChannelTemplateRequest;
import com.tencentcloudapi.essbasic.v20210526.models.OperateChannelTemplateResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

public class OperateChannelTemplate {

    public static OperateChannelTemplateResponse operateChannelTemplate(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            OperateChannelTemplateRequest req = new OperateChannelTemplateRequest();

            req.setAgent(agent);

            req.setTemplateId(TemplateId);

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

            Agent agent = setAgent();

            OperateChannelTemplateResponse templatesResponse = OperateChannelTemplate.operateChannelTemplate(agent, TemplateId);
            assert templatesResponse != null;
            System.out.println(OperateChannelTemplateResponse.toJsonString(templatesResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}