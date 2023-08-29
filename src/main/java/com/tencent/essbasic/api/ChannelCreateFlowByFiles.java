package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

public class ChannelCreateFlowByFiles {

    public static ChannelCreateFlowByFilesResponse channelCreateFlowByFiles
    (Agent agent, FlowApproverInfo[] flowApproverInfos, String flowName, String fileId) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateFlowByFilesRequest req = new ChannelCreateFlowByFilesRequest();

            req.setAgent(agent);

            req.setFlowApprovers(flowApproverInfos);

            req.setFlowName(flowName);

            req.setFileIds(new String[]{fileId});

            // 返回的resp是一个ChannelCreateFlowByFilesResponse的实例，与请求对象对应
            return client.ChannelCreateFlowByFiles(req);
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

            component.setComponentPage(1L);

            component.setComponentType("SIGN_SIGNATURE");

            component.setComponentValue("");
            
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
