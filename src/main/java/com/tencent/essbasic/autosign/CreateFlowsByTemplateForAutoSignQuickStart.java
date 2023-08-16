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


/**
 * 使用模板发起合同QuickStart 医疗自动签专用 B2CC
 * <p>
 * 文档地址：https://qian.tencent.com/developers/partnerApis/startFlows/CreateFlowsByTemplates
 * <p>
 * 此接口（CreateFlowsByTemplates）用于使用模板批量创建签署流程。
 * 当前可批量发起合同（签署流程）数量为1-20个。 如若在模板中配置了动态表格, 上传的附件必须为A4大小 合同发起人必须在电子签已经进行实名。
 * <p>
 * 本示例用于第三方应用集成接口对接，通过模板快速发起第一份合同
 * 建议配合文档进行操作，先修改config里的基本参数以及对应环境域名，然后跑一次
 * 第三方应用集成主要针对平台企业-代理子客发起合同，简要步骤主要是
 * 1. 通过CreateConsoleLoginUrl引导子客企业完成电子签的实名认证 - 子客企业在电子签配置印章等
 * 2. 通过简单封装的CreateFlowByTemplateDirectly接口快速发起一份合同，并得到签署链接
 * 3. 在小程序签署合同，通过API下载合同
 * 基于具体业务上的参数调用，可以参考官网的接口说明
 * https://cloud.tencent.com/document/product/1420/61534
 * 每个API的封装在api目录下可以自己配合相关参数进行调用
 */
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
        // 参与人类型 PERSON_AUTO_SIGN 个人自动签，需要先开通个人自动签
        approverInfo.setApproverType("PERSON_AUTO_SIGN");
        // 姓名 最大长度50个字符
        approverInfo.setName(name);
        // 手机号 脱敏显示。大陆手机号为11位，暂不支持海外手机号
        approverInfo.setMobile(mobile);
        // 身份证
        approverInfo.setIdCardType("ID_CARD");
        approverInfo.setIdCardNumber(idCardNumber);
        // 签署审批-false-无需审批
        approverInfo.setApproverNeedSignReview(false);
        // 设置模板中的参与方RecipientId
        approverInfo.setRecipientId(recipientId);

        return approverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo buildServerSignOrganizationApprover(String recipientId) {

        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        // 签署人类型
        // PERSON-个人/自然人；
        // ORGANIZATION-企业（企业签署方或模板发起时的企业静默签）；
        flowApproverInfo.setApproverType("ORGANIZATION");

        // 企业签署方工商营业执照上的企业名称，签署方为非发起方企业场景下必传，最大长度64个字符；
        flowApproverInfo.setOrganizationName(Config.ServerSignOrgName);
        // 如果签署方是子客企业，此处需要传子客企业的OrganizationOpenId
        // 企业签署方在同一平台下的其他合作企业OpenId，签署方为非发起方企业场景下必传，最大长度64个字符；
        flowApproverInfo.setOrganizationOpenId(Config.ProxyOrganizationOpenId);

        // 设置模板中的参与方RecipientId
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
