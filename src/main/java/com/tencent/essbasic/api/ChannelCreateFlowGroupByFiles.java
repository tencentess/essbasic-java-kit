package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

public class ChannelCreateFlowGroupByFiles {

    public static ChannelCreateFlowGroupByFilesResponse channelCreateFlowGroupByFiles(Agent agent, FlowApproverInfo[] flowApproverInfos, String flowName, String fileId, String flowGroupName) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowGroupByFilesRequest req = new ChannelCreateFlowGroupByFilesRequest();

            req.setAgent(agent);
            req.setFlowGroupName(flowGroupName);

            FlowFileInfo flowFileInfo = new FlowFileInfo();

            flowFileInfo.setFlowApprovers(flowApproverInfos);

            flowFileInfo.setFlowName(flowName);

            flowFileInfo.setFileIds(new String[]{fileId});

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
            String fileId = "********************************";


            String flowName = "我的第一份文件合同";

            String flowGroupName = "第一个合同组";

            Agent agent = CreateFlowUtils.setAgent();

            // 签署方参与信息
            FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

            flowApproverInfo.setApproverType("PERSON");

            flowApproverInfo.setName("*****");

            flowApproverInfo.setMobile("********************");

            // 模板控件信息
            // 签署人对应的签署控件

            Component component = new Component();

            component.setComponentPosX(146.15625F);

            component.setComponentPosY(472.78125F);

            component.setComponentWidth(112F);

            component.setComponentHeight(40F);

            component.setFileIndex(0L);

            component.setComponentType("SIGN_SIGNATURE");

            component.setComponentPage(1L);

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