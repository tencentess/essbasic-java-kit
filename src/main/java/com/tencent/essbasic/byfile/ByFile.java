package com.tencent.essbasic.byfile;

import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import com.tencentcloudapi.essbasic.v20210526.models.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ByFile
 */
public class ByFile {

    // 构造签署人 - 以个人为例, 实际请根据自己的场景构造签署方、控件
    public static FlowApproverInfo[] BuildApprovers() {

        // 个人签署方参数
        String personName = "******";
        String personMobile = "*************";

        // 企业签署方参数
        // String organizationName = "*********";
        // String organizationOpenId = "***************";
        // String openId = "*********";

        // 用列表存储(此处根据自己签署的类型选择对应的传入参数，
        // 如单c就只传入一次个人签署方，BtoC就传入一次个人签署方，一次企业签署方)
        List<FlowApproverInfo> flowApproverInfoList = new ArrayList<FlowApproverInfo>();

        // 传入个人签署方
        flowApproverInfoList.add(BuildPersonApprover(personName, personMobile));

        // 传入企业签署方
        // flowApproverInfoList.add(BuildOrganizationApprover(organizationName, organizationOpenId, openId));

        // 传入企业静默签署
        // flowApproverInfoList.add(BuildServerSignApprover());

        // 转换为对象数组
        FlowApproverInfo[] flowApproverInfo = new FlowApproverInfo[flowApproverInfoList.size()];

        for (int i = 0; i < flowApproverInfoList.size(); i++){
            flowApproverInfo[i] = flowApproverInfoList.get(i);
        }

        return flowApproverInfo;
    }

    // 打包个人签署方参与者信息
    public static FlowApproverInfo BuildPersonApprover(String name, String mobile) {
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

        // 控件，包括填充控件、签署控件，具体查看
	    // https://cloud.tencent.com/document/api/1420/61525#Component

        // 这里简单定义一个个人手写签名的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

        return flowApproverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo BuildOrganizationApprover(String organizationName, String organizationOpenId, String openId) {
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

        // 控件，包括填充控件、签署控件，具体查看
	    // https://cloud.tencent.com/document/api/1420/61525#Component

        // 这里简单定义一个个人手写签名的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

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

        // 控件，包括填充控件、签署控件，具体查看
	    // https://cloud.tencent.com/document/api/1420/61525#Component

        // 这里简单定义一个个人手写签名的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

        return flowApproverInfo;
    }

    // BuildComponent 构建（签署）控件信息
    // 详细参考 https://cloud.tencent.com/document/api/1420/61525#Component

    // 在通过文件发起合同时，对应的component有三种定位方式
    // 绝对定位方式
    // 表单域(FIELD)定位方式
    // 关键字(KEYWORD)定位方式
    // 可以参考官网说明
    // https://cloud.tencent.com/document/product/1323/78346#component-.E4.B8.89.E7.A7.8D.E5.AE.9A.E4.BD.8D.E6.96.B9.E5.BC.8F.E8.AF.B4.E6.98.8E
    public static Component BuildComponent(Float componentPosX, Float componentPosY, Float componentWidth,
                                           Float componentHeight, Long fileIndex, String componentType,
                                           Long componentPage, String componentValue) {

        Component component = new Component();

        // 位置信息 包括：
        // 参数控件X位置，单位px
        component.setComponentPosX(componentPosX);
        // 参数控件Y位置，单位px
        component.setComponentPosY(componentPosY);
        // 参数控件宽度，默认100，单位px，表单域和关键字转换控件不用填
        component.setComponentWidth(componentWidth);
        // 参数控件高度，默认100，单位px，表单域和关键字转换控件不用填
        component.setComponentHeight(componentHeight);
        // 控件所属文件的序号 (文档中文件的排列序号，从0开始)
        component.setFileIndex(fileIndex);
        // 参数控件所在页码，从1开始
        component.setComponentPage(componentPage);

        // 控件类型与对应值，这里以官网说明为准
		// https://cloud.tencent.com/document/api/1420/61525#Component
        component.setComponentType(componentType);
        component.setComponentValue(componentValue);

        return component;
    }
}
