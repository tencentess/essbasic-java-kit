package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 通过此接口（DescribeTemplates）查询该企业在电子签渠道版中配置的有效模板列表
 */
public class DescribeTemplates {
    /**
     * 查询该企业在电子签渠道版中配置的有效模板列表
     *
     * @param agent      渠道应用相关信息
     * @param TemplateId 模板唯一标识
     * @return DescribeTemplatesResponse
     */
    public static DescribeTemplatesResponse describeTemplates(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeTemplatesRequest req = new DescribeTemplatesRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 模板唯一标识
            req.setTemplateId(TemplateId);

            // 返回的resp是一个DescribeTemplatesResponse的实例，与请求对象对应
            DescribeTemplatesResponse resp = client.DescribeTemplates(req);
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

            DescribeTemplatesResponse templatesResponse = DescribeTemplates.describeTemplates(agent, TemplateId);
            assert templatesResponse != null;
            System.out.println(DescribeTemplatesResponse.toJsonString(templatesResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}