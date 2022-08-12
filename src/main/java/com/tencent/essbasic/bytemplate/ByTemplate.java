package com.tencent.essbasic.bytemplate;

import com.tencent.essbasic.api.DescribeTemplates;
import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeTemplatesResponse;
import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.essbasic.v20210526.models.Recipient;
import com.tencentcloudapi.essbasic.v20210526.models.TemplateInfo;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ByTemplate
 */
public class ByTemplate {
    // 构造签署人
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
                    approvers.add(BuildPersonApprover(personName,personMobile, recipient.getRecipientId()));
                    break;
                case "INDIVIDUAL":
                    approvers.add(BuildOrganizationApprover(organizationName, organizationOpenId, openId, recipient.getRecipientId()));
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
        // 签署人类型，PERSON-个人；
        // ORGANIZATION-企业；
        // ENTERPRISESERVER-企业静默签;
        // 注：ENTERPRISESERVER 类型仅用于使用文件创建流程（ChannelCreateFlowByFiles）接口；并且仅能指定发起方企业签署方为静默签署；
        flowApproverInfo.setApproverType("PERSON");
        // 本环节需要操作人的名字
        flowApproverInfo.setName(name);
        // 本环节需要操作人的手机号
        flowApproverInfo.setMobile(mobile);
        flowApproverInfo.setRecipientId(recipientId);


        return flowApproverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo BuildOrganizationApprover(String organizationName, String organizationOpenId,
                                                             String openId, String recipientId) {

        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();
        // 签署人类型，PERSON-个人；
        // ORGANIZATION-企业；
        // ENTERPRISESERVER-企业静默签;
        // 注：ENTERPRISESERVER 类型仅用于使用文件创建流程（ChannelCreateFlowByFiles）接口；并且仅能指定发起方企业签署方为静默签署；
        flowApproverInfo.setApproverType("ORGANIZATION");
        // 本环节需要企业操作人的企业名称
        flowApproverInfo.setOrganizationName(organizationName);
        //
        flowApproverInfo.setOrganizationOpenId(organizationOpenId);
        flowApproverInfo.setOpenId(openId);
        flowApproverInfo.setRecipientId(recipientId);


        return flowApproverInfo;
    }

    // 打包企业静默签署方参与者信息
    public static FlowApproverInfo BuildServerSignApprover() {
        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();
        // 签署人类型，PERSON-个人；
        // ORGANIZATION-企业；
        // ENTERPRISESERVER-企业静默签;
        // 注：ENTERPRISESERVER 类型仅用于使用文件创建流程（ChannelCreateFlowByFiles）接口；并且仅能指定发起方企业签署方为静默签署；
        flowApproverInfo.setApproverType("ENTERPRISESERVER");

        return flowApproverInfo;
    }

    //获取所有签署人信息
    public static Recipient[] GetRecipients(String templateId) {
        DescribeTemplatesResponse templatesResponse = DescribeTemplates.describeTemplates(CreateFlowUtils.setAgent(),
                templateId);

        return  Optional.ofNullable(templatesResponse).filter(template -> template.getTemplates().length > 0).
                map(DescribeTemplatesResponse::getTemplates).map(rec -> rec[0].getRecipients()).orElse(null);
    }

}
