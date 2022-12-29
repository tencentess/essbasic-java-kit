package com.tencent.essbasic.bytemplate;

import com.tencent.essbasic.api.DescribeTemplates;
import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.essbasic.v20210526.models.*;
import com.tencent.essbasic.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ByTemplate {
    // 构造签署人 - 以B2B2C为例, 实际请根据自己的场景构造签署方、控件
    public static FlowApproverInfo[] BuildApprovers(List<Recipient> recipients) {

        List<FlowApproverInfo> approvers = new ArrayList<>();

        // 个人签署方参数
        String personName = "******";
        String personMobile = "*************";

        // 企业签署方参数
        String organizationName = "**********";
        String organizationOpenId = Config.ProxyOrganizationOpenId;
        String openId = Config.ProxyOperatorOpenId;

        for (Recipient recipient : recipients) {
            switch (recipient.getRecipientType()) {
                case "ENTERPRISE":
                    // 另一家企业签署方
                    approvers.add(BuildOrganizationApprover(organizationName, organizationOpenId, openId, recipient.getRecipientId()));
                    break;
                case "INDIVIDUAL":
                    // 个人签署方
                    approvers.add(BuildPersonApprover(personName,personMobile, recipient.getRecipientId()));
                    break;

            }
        }

        // 转换为对象数组
        return approvers.toArray(new FlowApproverInfo[0]);
    }

    // 打包个人签署方参与者信息
    public static FlowApproverInfo BuildPersonApprover(String name, String mobile, String recipientId) {

        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();
        
        // 签署人类型
        // PERSON-个人/自然人；
        // ORGANIZATION-企业（企业签署方或模版发起时的企业静默签）；
        // ENTERPRISESERVER-企业静默签（文件发起时的企业静默签字）。
        flowApproverInfo.setApproverType("PERSON");

        // 签署人姓名，最大长度50个字符
        flowApproverInfo.setName(name);
        // 签署人手机号，脱敏显示。大陆手机号为11位，暂不支持海外手机号
        flowApproverInfo.setMobile(mobile);

        // 设置模版中的参与方RecipientId
        flowApproverInfo.setRecipientId(recipientId);

        return flowApproverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo BuildOrganizationApprover(String organizationName, String organizationOpenId,
                                                             String openId, String recipientId) {

        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        // 签署人类型
        // PERSON-个人/自然人；
        // ORGANIZATION-企业（企业签署方或模版发起时的企业静默签）；
        // ENTERPRISESERVER-企业静默签（文件发起时的企业静默签字）。
        flowApproverInfo.setApproverType("ORGANIZATION");

        // 企业签署方工商营业执照上的企业名称，签署方为非发起方企业场景下必传，最大长度64个字符；
        flowApproverInfo.setOrganizationName(organizationName);
        // 如果签署方是子客企业，此处需要传子客企业的OrganizationOpenId
        // 企业签署方在同一渠道下的其他合作企业OpenId，签署方为非发起方企业场景下必传，最大长度64个字符；
        flowApproverInfo.setOrganizationOpenId(organizationOpenId);
        // 如果签署方是子客企业，此处需要传子客企业经办人的OpenId
	    // 当签署方为同一渠道下的员工时，该字段若不指定，则发起【待领取】的流程
        flowApproverInfo.setOpenId(openId);

        // 设置模版中的参与方RecipientId
        flowApproverInfo.setRecipientId(recipientId);

        return flowApproverInfo;
    }

    // 打包企业静默签署方参与者信息
    public static FlowApproverInfo BuildServerSignApprover() {
        // 签署参与者信息
        // 企业静默签
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        // 签署人类型
        // PERSON-个人/自然人；
        // ORGANIZATION-企业（企业签署方或模版发起时的企业静默签）；
        // ENTERPRISESERVER-企业静默签（文件发起时的企业静默签字）。
        flowApproverInfo.setApproverType("ENTERPRISESERVER");

        // 注：此时发起方会替换为接口调用的企业+经办人，所以不需要传签署方信息

        return flowApproverInfo;
    }

    // 从模板中获取参与人信息，用于模板发起合同
    public static Recipient[] GetRecipients(String templateId) {
        DescribeTemplatesResponse templatesResponse = DescribeTemplates.describeTemplates(CreateFlowUtils.setAgent(),
                templateId);

        return  Optional.ofNullable(templatesResponse).filter(template -> template.getTemplates().length > 0).
                map(DescribeTemplatesResponse::getTemplates).map(rec -> rec[0].getRecipients()).orElse(null);
    }

    // 内容控件填充结构，详细说明参考
    // https://cloud.tencent.com/document/api/1420/61525#FormField
    public static FormField BuildFormField(String componentName, String componentValue){
        FormField formField = new FormField();
        formField.setComponentName(componentName);
        formField.setComponentValue(componentValue);
        return formField;
    }
}
