package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 用于通过多文件创建合同组签署流程。
 * 详细参考 https://cloud.tencent.com/document/api/1420/80390
 */
public class ChannelCreateFlowGroupByFiles {
    /**
     * 用于通过多文件创建合同组签署流程。
     *
     * @param agent             第三方平台应用相关信息
     * @param flowApproverInfos 签署流程签约方列表
     * @param flowName          签署流程名称
     * @param fileId            签署文件资源Id
     * @param flowGroupName     合同组名称，长度不超过200个字符
     * @return ChannelCreateFlowGroupByFilesResponse
     */
    public static ChannelCreateFlowGroupByFilesResponse channelCreateFlowGroupByFiles(Agent agent, FlowApproverInfo[] flowApproverInfos, String flowName, String fileId, String flowGroupName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowGroupByFilesRequest req = new ChannelCreateFlowGroupByFilesRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            req.setFlowGroupName(flowGroupName);

            FlowFileInfo flowFileInfo = new FlowFileInfo();
            // 签署流程签约方列表，最多不超过5个参与方
            flowFileInfo.setFlowApprovers(flowApproverInfos);
            // 签署流程名称，长度不超过200个字符
            flowFileInfo.setFlowName(flowName);
            // 签署文件资源Id列表，目前仅支持单个文件
            flowFileInfo.setFileIds(new String[]{fileId});

            // 每个子合同的发起所需的信息，数量限制2-100
	        // 详细参考 https://cloud.tencent.com/document/product/1420/61534
            req.setFlowFileInfos(new FlowFileInfo[]{flowFileInfo});
            
            // 返回的resp是一个ChannelCreateFlowGroupByFilesResponse的实例，与请求对象对应
            return client.ChannelCreateFlowGroupByFiles(req);
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
            // 从UploadFiles接口获取到的fileId
            String fileId = "********************************";

            // 签署流程名称,最大长度200个字符
            String flowName = "我的第一份文件合同";

            String flowGroupName = "第一个合同组";

            // 第三方平台应用相关信息
            Agent agent = CreateFlowUtils.setAgent();

            // 签署方参与信息
            FlowApproverInfo flowApproverInfo = new FlowApproverInfo();
            // 签署人类型，PERSON-个人；
            // ORGANIZATION-企业；
            // ENTERPRISESERVER-企业静默签;
            // 注：ENTERPRISESERVER 类型仅用于使用文件创建流程（ChannelCreateFlowByFiles）接口；并且仅能指定发起方企业签署方为静默签署；
            flowApproverInfo.setApproverType("PERSON");
            // 本环节需要操作人的名字
            flowApproverInfo.setName("*****");
            // 本环节需要操作人的手机号
            flowApproverInfo.setMobile("********************");

            // 模板控件信息
            // 签署人对应的签署控件

            Component component = new Component();

            // 参数控件X位置，单位px
            component.setComponentPosX(146.15625F);
            // 参数控件Y位置，单位px
            component.setComponentPosY(472.78125F);
            // 参数控件宽度，默认100，单位px，表单域和关键字转换控件不用填
            component.setComponentWidth(112F);
            // 参数控件高度，默认100，单位px，表单域和关键字转换控件不用填
            component.setComponentHeight(40F);
            // 控件所属文件的序号 (文档中文件的排列序号，从0开始)
            component.setFileIndex(0L);
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
            component.setComponentType("SIGN_SIGNATURE");
            // 参数控件所在页码，从1开始
            component.setComponentPage(1L);
            // 印章 ID，传参 DEFAULT_COMPANY_SEAL 表示使用默认印章。
            // 控件填入内容，印章控件里面，如果是手写签名内容为PNG图片格式的base64编码。
            component.setComponentValue("");
            Component[] components = new Component[]{component};
            flowApproverInfo.setSignComponents(components);
            FlowApproverInfo[] flowApproverInfos = new FlowApproverInfo[]{flowApproverInfo};

            ChannelCreateFlowGroupByFilesResponse channelCreateFlowGroupByFilesRes = ChannelCreateFlowGroupByFiles.channelCreateFlowGroupByFiles(agent, flowApproverInfos, flowName, fileId, flowGroupName);
            assert channelCreateFlowGroupByFilesRes != null;
            System.out.println(ChannelCreateFlowGroupByFilesResponse.toJsonString(channelCreateFlowGroupByFilesRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}