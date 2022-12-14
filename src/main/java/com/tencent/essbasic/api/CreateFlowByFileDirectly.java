package com.tencent.essbasic.api;

import com.tencentcloudapi.essbasic.v20210526.models.*;

import java.util.HashMap;
import java.util.Map;

import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 通过文件base64直接发起签署流程，返回flowId和签署链接
 * 本接口是对于发起合同几个接口的封装，详细参数需要根据自身业务进行调整
 * UploadFiles--ChannelCreateFlowByFiles--CreateSignUrls
 */
public class CreateFlowByFileDirectly {
    /**
     * 通过文件base64直接发起签署流程
     *
     * @param fileBase64        Base64编码后的文件内容
     * @param flowApproverInfos 签署流程签约方列表
     * @param flowName          签署流程名称
     * @return Map<String, String>
     */
    public static Map<String, String> createFlowByFileDirectly(String flowName, String fileBase64,
                                                               FlowApproverInfo[] flowApproverInfos) {
        Map<String, String> resp = new HashMap<>();
        // 设置agent参数
        Agent agent = setAgent();

        // 设置uploadFile参数,这里可以修改传入数量
        UploadFile[] uploadFiles = new UploadFile[]{new UploadFile()};
        uploadFiles[0].setFileBody(fileBase64);
        // 上传文件获取fileId
        UploadFilesResponse uploadRes = UploadFiles.uploadFiles(agent, uploadFiles);

        // fileId
        assert uploadRes != null;
        String fileId = uploadRes.getFileIds()[0];


        // 创建签署流程
        ChannelCreateFlowByFilesResponse createFlowRes = ChannelCreateFlowByFiles.
                channelCreateFlowByFiles(agent, flowApproverInfos, flowName, fileId);

        // 获取flowId
        assert createFlowRes != null;
        String flowId = createFlowRes.getFlowId();


        // 获取签署链接
        String[] flowIds = new String[1];
        flowIds[0] = flowId;
        CreateSignUrlsResponse createSignUrlsRes = CreateSignUrls.createSignUrls(flowIds, agent);
        assert createSignUrlsRes != null;
        String url = createSignUrlsRes.getSignUrlInfos()[0].getSignUrl();

        resp.put("flowId", flowId);
        resp.put("url", url);

        return resp;
    }

}
