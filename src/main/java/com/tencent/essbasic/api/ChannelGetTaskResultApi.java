package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelGetTaskResultApiRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelGetTaskResultApiResponse;

/**
 * 渠道版查询转换任务状态
 * 详细参考 https://cloud.tencent.com/document/api/1420/78773
 */
public class ChannelGetTaskResultApi {
    /**
     * 渠道版查询转换任务状态
     *
     * @param agent  渠道应用相关信息
     * @param TaskId 任务Id，通过ChannelCreateConvertTaskApi接口获得
     * @return ChannelGetTaskResultApiResponse
     */
    public static ChannelGetTaskResultApiResponse channelGetTaskResultApi(String TaskId, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelGetTaskResultApiRequest req = new ChannelGetTaskResultApiRequest();

            // 渠道应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 任务Id，通过ChannelCreateConvertTaskApi接口获得
            req.setTaskId(TaskId);

            // 返回的resp是一个ChannelGetTaskResultApiResponse的实例，与请求对象对应
            return client.ChannelGetTaskResultApi(req);
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
            // 任务Id，通过ChannelCreateConvertTaskApi接口获得
            String TaskId = "*********************";
            ChannelGetTaskResultApiResponse channelGetTaskResultApiRes = ChannelGetTaskResultApi.channelGetTaskResultApi(TaskId, agent);
            assert channelGetTaskResultApiRes != null;
            System.out.println(ChannelGetTaskResultApiResponse.toJsonString(channelGetTaskResultApiRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}