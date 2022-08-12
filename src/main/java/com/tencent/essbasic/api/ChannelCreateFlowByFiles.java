package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 用来通过上传后的pdf资源编号来创建待签署的合同流程。
 * 适用场景1：适用非制式的合同文件签署。一般开发者自己有完整的签署文件，可以通过该接口传入完整的PDF文件及流程信息生成待签署的合同流程。
 * 适用场景2：可通过改接口传入制式合同文件，同时在指定位置添加签署控件。可以起到接口创建临时模板的效果。如果是标准的制式文件，建议使用模板功能生成模板ID进行合同流程的生成。
 * 注意事项：该接口需要依赖“多文件上传”接口生成pdf资源编号（FileIds）进行使用。
 */

public class ChannelCreateFlowByFiles {
    /**
     * 通过上传后的pdf资源编号来创建待签署的合同流程
     *
     * @param agent             渠道应用相关信息
     * @param flowApproverInfos 签署流程签约方列表
     * @param flowName          签署流程名称
     * @param fileId            签署文件资源Id
     * @return ChannelCreateFlowByFilesResponse
     */
    public static ChannelCreateFlowByFilesResponse channelCreateFlowByFiles
    (Agent agent, FlowApproverInfo[] flowApproverInfos, String flowName, String fileId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowByFilesRequest req = new ChannelCreateFlowByFilesRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 签署流程签约方列表，最多不超过5个参与方
            req.setFlowApprovers(flowApproverInfos);
            // 签署流程名称，长度不超过200个字符
            req.setFlowName(flowName);
            // 签署文件资源Id列表，目前仅支持单个文件
            req.setFileIds(new String[]{fileId});

            // 返回的resp是一个ChannelCreateFlowByFilesResponse的实例，与请求对象对应
            ChannelCreateFlowByFilesResponse resp = client.ChannelCreateFlowByFiles(req);
            return resp;
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

            // 渠道应用相关信息
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
            component.setComponentValue("componentValue");
            Component[] components = new Component[]{component};
            flowApproverInfo.setSignComponents(components);
            FlowApproverInfo[] flowApproverInfos = new FlowApproverInfo[]{flowApproverInfo};

            ChannelCreateFlowByFilesResponse createFlowRes = ChannelCreateFlowByFiles.
                    channelCreateFlowByFiles(agent, flowApproverInfos, flowName, fileId);

            assert createFlowRes != null;
            System.out.println(ChannelCreateFlowByFilesResponse.toJsonString(createFlowRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
