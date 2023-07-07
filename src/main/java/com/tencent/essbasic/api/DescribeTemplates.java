package com.tencent.essbasic.api;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.initClient;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 通过此接口（DescribeTemplates）查询该企业在电子签第三方应用集成中配置的有效模板列表
 * 详细参考 https://cloud.tencent.com/document/api/1420/61521
 */
public class DescribeTemplates {
    /**
     * 查询该企业在电子签第三方应用集成中配置的有效模板列表
     *
     * @param agent      第三方平台应用相关信息
     * @param TemplateId 模板唯一标识
     * @return DescribeTemplatesResponse
     */
    public static DescribeTemplatesResponse describeTemplates(Agent agent, String TemplateId) {
        try {
            // 实例化一个client
            EssbasicClient client = initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeTemplatesRequest req = new DescribeTemplatesRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 必填。
            req.setAgent(agent);
            // 模板唯一标识，查询单个模板时使用
            req.setTemplateId(TemplateId);

            // 其他查询参数参考官网文档
	        // https://cloud.tencent.com/document/api/1420/61521

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