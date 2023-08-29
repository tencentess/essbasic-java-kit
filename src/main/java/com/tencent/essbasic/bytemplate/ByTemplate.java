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
        
        flowApproverInfo.setApproverType("PERSON");

        flowApproverInfo.setName(name);

        flowApproverInfo.setMobile(mobile);

        flowApproverInfo.setRecipientId(recipientId);

        return flowApproverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo BuildOrganizationApprover(String organizationName, String organizationOpenId,
                                                             String openId, String recipientId) {

        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        flowApproverInfo.setApproverType("ORGANIZATION");

        flowApproverInfo.setOrganizationName(organizationName);

        flowApproverInfo.setOrganizationOpenId(organizationOpenId);

        flowApproverInfo.setOpenId(openId);

        flowApproverInfo.setRecipientId(recipientId);

        return flowApproverInfo;
    }

    // 打包企业静默签署方参与者信息
    public static FlowApproverInfo BuildServerSignApprover() {
        // 签署参与者信息
        // 企业静默签
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

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

    public static FormField BuildFormField(String componentName, String componentValue){
        FormField formField = new FormField();
        formField.setComponentName(componentName);
        formField.setComponentValue(componentValue);
        return formField;
    }
}
