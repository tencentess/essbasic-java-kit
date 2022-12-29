package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateConvertTaskApiRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateConvertTaskApiResponse;

/**
 * 渠道创建文件转换任务
 * 详细参考 https://cloud.tencent.com/document/api/1420/78774
 */
public class ChannelCreateConvertTaskApi {
    /**
     * 渠道创建文件转换任务
     *
     * @param agent        渠道应用相关信息
     * @param ResourceType 资源类型 取值范围doc,docx,html之一
     * @param ResourceName 资源名称，长度限制为256字符
     * @param ResourceId   资源Id，通过UploadFiles获取
     * @return ChannelCreateConvertTaskApiResponse
     */
    public static ChannelCreateConvertTaskApiResponse channelCreateConvertTaskApi(String ResourceType, String ResourceName, String ResourceId, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelCreateConvertTaskApiRequest req = new ChannelCreateConvertTaskApiRequest();

            // 渠道应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 资源类型 取值范围doc,docx,html,xls,xlsx之一
            req.setResourceType(ResourceType);
            // 资源名称，长度限制为256字符
            req.setResourceName(ResourceName);
            // 资源Id，通过UploadFiles获取
            req.setResourceId(ResourceId);

            // 返回的resp是一个ChannelCreateConvertTaskApiResponse的实例，与请求对象对应
            return client.ChannelCreateConvertTaskApi(req);
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
            // 设置agent参数
            Agent agent = CreateFlowUtils.setAgent();
            // 发起合同成功的签署流程Id
            String ResourceType = "docx";
            String ResourceName = "hello.docx";
            // 从UploadFiles接口获取到的ResourceId
            String ResourceId = "********************************";

            ChannelCreateConvertTaskApiResponse channelCreateConvertTaskApiRes = ChannelCreateConvertTaskApi.channelCreateConvertTaskApi(ResourceType, ResourceName, ResourceId, agent);
            assert channelCreateConvertTaskApiRes != null;
            System.out.println(ChannelCreateConvertTaskApiResponse.toJsonString(channelCreateConvertTaskApiRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}