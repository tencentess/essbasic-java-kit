package com.tencent.essbasic.autosign;


import com.tencent.essbasic.api.CreateFlowsByTemplates;
import com.tencent.essbasic.api.DescribeFileUrls;
import com.tencent.essbasic.bytemplate.ByTemplate;
import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import java.util.Arrays;
import java.util.List;

import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;


public class CreateFlowsByTemplateForAutoSignQuickStart {
    public static void main(String[] args) {
        try {
            // Step 1
            // 定义合同名
            String flowName = "模板发起-医疗自动签署测试合同-" + System.currentTimeMillis();

            // 模板Id，根据自己传入的模板需求修改第三个参数为对应的TemplateId，在Config中配置
            String templateId = Config.TemplateId;

            // 获取模板里面的参与方RecipientId
            Recipient[] recipients = ByTemplate.GetRecipients(templateId);
            if (recipients == null) {
                throw new NullPointerException("签署人不能为空");
            }

            // 构造自动签签署人
            // 此块代码中的$approvers仅用于快速发起一份合同样例，非正式对接用
            // 医生的信息
            // 个人签署方的姓名，必须是真实的才能正常签署
            FlowApproverInfo personApprover1 = GetAutoSignPersonApprover(Arrays.asList(recipients), "医生",
                    "周岩", "19180497760", "230503198812100046");

            // 药师的信息
            FlowApproverInfo personApprover2 = GetAutoSignPersonApprover(Arrays.asList(recipients), "药师",
                    "药师姓名", "药师手机号", "药师身份证号");

            // 平台静默签署
            FlowApproverInfo serverApprover = GetServerSignApprover(Arrays.asList(recipients));

            FlowApproverInfo[] allApprovers = new FlowApproverInfo[]{personApprover1, personApprover2, serverApprover};

            // Step 2
            // 发起合同
            String[] flowIds = createFlowByTemplateForAutoSignDirectly(flowName, templateId, allApprovers);

            // Step 3
            // 下载合同
            int count = Config.COUNT;
            for (int i = 0; i < count; i++) {
                // 返回合同Id
                System.out.println("您创建的合同id为：");
                System.out.println(flowIds[i]);
                System.out.println("\r\n\r\n");

                String url = DescribeFileUrls.describeFileUrls(flowIds[i]);
                System.out.println("请访问以下地址下载您的合同：");
                System.out.println(url);
                System.out.println("\r\n\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 构造签署人 - 以B2CC为例, 实际请根据自己的场景构造签署方、控件

    /**
     * 构造签署人 - 以B2CC为例, 实际请根据自己的场景构造签署方、控件
     * 构造个人自动签参与人
     *
     * @param recipients 模板参与人
     * @return FlowApproverInfo 企业静默签署方
     */
    public static FlowApproverInfo GetServerSignApprover(List<Recipient> recipients) {

        for (Recipient recipient : recipients) {
            if (recipient.getRecipientType().equals("ENTERPRISE")) {
                return buildServerSignOrganizationApprover(recipient.getRecipientId());
            }
        }

        throw new NullPointerException("模板企业参与方不能为空");
    }

    /**
     * 构造签署人 - 以B2CC为例, 实际请根据自己的场景构造签署方、控件
     * 构造个人自动签参与人
     *
     * @param recipients   模板参与人
     * @param roleName     模板中配置的参与方名称，这里以医生、药师为例，可根据模板实际情况修改
     * @param name         姓名
     * @param mobile       手机号
     * @param idCardNumber 身份证号
     * @return FlowApproverInfo 个人自动签署方
     */
    public static FlowApproverInfo GetAutoSignPersonApprover(List<Recipient> recipients, String roleName,
                                                             String name, String mobile, String idCardNumber) {

        for (Recipient recipient : recipients) {
            if (recipient.getRoleName().equals(roleName)) {
                return buildAutoSignPersonApprover(name, mobile, idCardNumber, recipient.getRecipientId());
            }
        }

        throw new NullPointerException("模板参与方：" + roleName + " 不能为空");
    }

    // 打包个人签署方参与者信息
    public static FlowApproverInfo buildAutoSignPersonApprover(String name, String mobile, String idCardNumber,
                                                               String recipientId) {

        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo approverInfo = new FlowApproverInfo();

        approverInfo.setApproverType("PERSON_AUTO_SIGN");

        approverInfo.setName(name);

        approverInfo.setMobile(mobile);

        approverInfo.setIdCardType("ID_CARD");
        approverInfo.setIdCardNumber(idCardNumber);

        approverInfo.setApproverNeedSignReview(false);

        approverInfo.setRecipientId(recipientId);

        return approverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo buildServerSignOrganizationApprover(String recipientId) {

        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        flowApproverInfo.setApproverType("ORGANIZATION");


        flowApproverInfo.setOrganizationName(Config.ServerSignOrgName);

        flowApproverInfo.setOrganizationOpenId(Config.ProxyOrganizationOpenId);

        flowApproverInfo.setRecipientId(recipientId);

        return flowApproverInfo;
    }

    /**
     * 通过文件base64直接发起签署流程
     *
     * @param flowName   签署流程名称
     * @param templateId 模板唯一标识
     * @return String[] flowIds
     */
    public static String[] createFlowByTemplateForAutoSignDirectly(String flowName,
                                                                   String templateId,
                                                                   FlowApproverInfo[] flowApproverInfos) {
        // 设置agent参数
        Agent agent = setAgent();
        // 创建签署流程
        // 签署数量
        int count = Config.COUNT;
        FlowInfo[] FlowInfos = new FlowInfo[count];
        for (int i = 0; i < count; i++) {
            // 构建内容控件填充结构(根据自己需求使用)
            FlowInfo info = CreateFlowUtils.fillFlowInfo(templateId, flowName, flowApproverInfos);
            info.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");
            FlowInfos[i] = info;
        }

        // 发起签署
        CreateFlowsByTemplatesResponse flowResponse = CreateFlowsByTemplates.createFlowsByTemplates(agent, FlowInfos);
        assert flowResponse != null;
        if (flowResponse.getErrorMessages().length > 0 && !flowResponse.getErrorMessages()[0].equals("")) {
            throw new NullPointerException(flowResponse.getErrorMessages()[0]);
        }
        return flowResponse.getFlowIds();
    }
}
