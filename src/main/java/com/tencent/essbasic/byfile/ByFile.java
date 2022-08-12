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
//        String organizationName = "*********";
//        String organizationOpenId = "***************";
//        String openId = "*********";

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
        // 签署人类型，PERSON-个人；
        // ORGANIZATION-企业；
        // ENTERPRISESERVER-企业静默签;
        // 注：ENTERPRISESERVER 类型仅用于使用文件创建流程（ChannelCreateFlowByFiles）接口；并且仅能指定发起方企业签署方为静默签署；
        flowApproverInfo.setApproverType("PERSON");
        // 本环节需要操作人的名字
        flowApproverInfo.setName(name);
        // 本环节需要操作人的手机号
        flowApproverInfo.setMobile(mobile);

        // 模板控件信息
        // 签署人对应的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

        return flowApproverInfo;
    }

    // 打包企业签署方参与者信息
    public static FlowApproverInfo BuildOrganizationApprover(String organizationName, String organizationOpenId, String openId) {

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

        // 模板控件信息
        // 签署人对应的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

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

        // 模板控件信息
        // 签署人对应的签署控件
        Component componentAdd = BuildComponent(146.15625F, 472.78125F, 112F,
                40F, 0L, "SIGN_SIGNATURE", 1L, "");
        Component[] component = new Component[]{componentAdd};
        flowApproverInfo.setSignComponents(component);

        return flowApproverInfo;
    }


    // 构建（签署）控件信息
    public static Component BuildComponent(Float componentPosX, Float componentPosY, Float componentWidth,
                                           Float componentHeight, Long fileIndex, String componentType,
                                           Long componentPage, String componentValue) {

        Component component = new Component();

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
        // 如果是Component控件类型，则可选的字段为：
        //TEXT - 普通文本控件；
        //DATE - 普通日期控件；跟TEXT相比会有校验逻辑
        //DYNAMIC_TABLE- 动态表格控件
        //如果是SignComponent控件类型，则可选的字段为
        //SIGN_SEAL - 签署印章控件；
        //SIGN_DATE - 签署日期控件；
        //SIGN_SIGNATURE - 用户签名控件；
        //SIGN_PERSONAL_SEAL - 个人签署印章控件；
        //表单域的控件不能作为印章和签名控件
        component.setComponentType(componentType);
        // 参数控件所在页码，从1开始
        component.setComponentPage(componentPage);
        // 印章 ID，传参 DEFAULT_COMPANY_SEAL 表示使用默认印章。
        // 控件填入内容，印章控件里面，如果是手写签名内容为PNG图片格式的base64编码。
        component.setComponentValue(componentValue);

        return component;
    }
}
