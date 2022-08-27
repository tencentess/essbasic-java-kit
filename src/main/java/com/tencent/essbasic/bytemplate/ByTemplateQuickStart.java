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
import java.util.Optional;

import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 使用模板发起合同QuickStart
 */
public class ByTemplateQuickStart {

    /**
     * ByTemplateQuickStart
     */
    public static void main(String[] args) {
        try {

            // Step 1
            // 定义合同名
            String flowName = "我的第一个合同";
            // 渠道侧合作企业名称
            String proxyOrganizationName = "我的企业";

            // 模板Id，根据自己传入的模板需求修改第三个参数为对应的TemplateId，在Config中配置
            String templateId = Config.TemplateId;

            // 创建控制台链接
            CreateConsoleLoginUrlResponse loginUrlResponse =
                    CreateConsoleLoginUrl.createConsoleLoginUrl(setAgent(), proxyOrganizationName);

            //Step 2
            // 获取模板里面的RecipientId
            Recipient[] recipients = ByTemplate.GetRecipients(templateId);
            if (recipients == null) {
                throw new NullPointerException("签署人不能为空");
            }

            //step 3
            //构造签署人信息
            FlowApproverInfo[] flowApproverInfos = ByTemplate.BuildApprovers(Arrays.asList(recipients));

            // Step 4
            // 发起合同
            // 样例为BtoC
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
                // Step 5
                // 下载合同
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
