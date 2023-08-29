package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

public class DescribeTemplates {

    public static DescribeTemplatesResponse describeTemplates(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeTemplatesRequest req = new DescribeTemplatesRequest();

            req.setAgent(agent);

            req.setTemplateId(TemplateId);

            // 返回的resp是一个DescribeTemplatesResponse的实例，与请求对象对应
            return client.DescribeTemplates(req);
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

            DescribeTemplatesResponse templatesResponse = DescribeTemplates.describeTemplates(agent, TemplateId);
            assert templatesResponse != null;
            System.out.println(DescribeTemplatesResponse.toJsonString(templatesResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}