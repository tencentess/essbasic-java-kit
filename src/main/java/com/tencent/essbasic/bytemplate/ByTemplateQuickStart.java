package com.tencent.essbasic.bytemplate;

import com.tencent.essbasic.api.CreateConsoleLoginUrl;
import com.tencent.essbasic.api.CreateFlowByTemplateDirectly;
import com.tencent.essbasic.api.DescribeFileUrls;
import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.essbasic.v20210526.models.CreateConsoleLoginUrlResponse;
import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import com.tencentcloudapi.essbasic.v20210526.models.Recipient;

import java.util.Arrays;
import java.util.Map;

import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/*
本示例用于渠道版接口对接，通过模板快速发起第一份合同
建议配合文档进行操作，先修改config里的基本参数以及对应环境域名，然后跑一次
渠道版主要针对渠道企业-代理子客发起合同，简要步骤主要是
    1. 通过CreateConsoleLoginUrl引导子客企业完成电子签的实名认证 - 子客企业在电子签配置印章等
    2. 通过简单封装的CreateFlowByTemplateDirectly接口快速发起一份合同，并得到签署链接
    3. 在小程序签署合同，通过API下载合同
基于具体业务上的参数调用，可以参考官网的接口说明 
https://cloud.tencent.com/document/product/1420/61534
每个API的封装在api目录下可以自己配合相关参数进行调用
*/
public class ByTemplateQuickStart {

    /**
     * ByTemplateQuickStart
     */
    public static void main(String[] args) {
        try {

            // Step 1 登录子客控制台
            // 渠道子客企业真实名称
            String proxyOrganizationName = "我的企业";

            // 创建控制台链接
            CreateConsoleLoginUrlResponse loginUrlResponse =
                    CreateConsoleLoginUrl.createConsoleLoginUrl(setAgent(), proxyOrganizationName);

            //Step 2 发合同
            // 定义合同名
            String flowName = "我的第一个合同";
            // 模板Id，根据自己传入的模板需求修改第三个参数为对应的TemplateId，在Config中配置
            String templateId = Config.TemplateId;

            // 获取模板里面的参与方RecipientId
            Recipient[] recipients = ByTemplate.GetRecipients(templateId);
            if (recipients == null) {
                throw new NullPointerException("签署人不能为空");
            }

            //构造签署人信息
            FlowApproverInfo[] flowApproverInfos = ByTemplate.BuildApprovers(Arrays.asList(recipients));

            // 发起合同 样例为BtoC
            Map<String, String[]> resp = CreateFlowByTemplateDirectly.createFlowByTemplateDirectly(flowName
                    , templateId, flowApproverInfos);

            //  返回相关信息
            System.out.println("您的控制台入口为：");
            System.out.println(loginUrlResponse.getConsoleUrl());
            System.out.println("\r\n\r\n");
            int count = Config.COUNT;
            for (int i = 0; i < count; i++) {
                // 返回合同Id
                System.out.println("您创建的合同id为：");
                System.out.println(resp.get("FlowIds")[i]);
                System.out.println("\r\n\r\n");
                
                // 返回签署的链接
                System.out.println("签署链接为：");
                System.out.println(resp.get("Urls")[i]);
                System.out.println("\r\n\r\n");

                // Step 3 下载合同
                // 返回合同下载链接
                String url = DescribeFileUrls.describeFileUrls(resp.get("FlowIds")[i]);
                System.out.println("请访问以下地址下载您的合同：");
                System.out.println(url);
                System.out.println("\r\n\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
