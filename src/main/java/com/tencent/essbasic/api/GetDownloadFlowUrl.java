package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DownloadFlowInfo;
import com.tencentcloudapi.essbasic.v20210526.models.GetDownloadFlowUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.GetDownloadFlowUrlResponse;

/**
 * 此接口（GetDownloadFlowUrl）用于创建电子签批量下载地址，让合作企业进入控制台直接下载，支持客户合同（流程）按照自定义文件夹形式 分类下载。
 * 当前接口限制最多合同（流程）50个.
 * 详细参考 https://cloud.tencent.com/document/api/1420/66368
 */
public class GetDownloadFlowUrl {
    /**
     * 创建电子签批量下载地址，让合作企业进入控制台直接下载
     *
     * @param agent             第三方平台应用相关信息
     * @param DownloadFlowInfos 文件夹数组，签署流程总数不能超过50个，一个文件夹下，不能超过20个签署流程
     * @return GetDownloadFlowUrlResponse
     */
    public static GetDownloadFlowUrlResponse getDownloadFlowUrl(DownloadFlowInfo[] DownloadFlowInfos, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            GetDownloadFlowUrlRequest req = new GetDownloadFlowUrlRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 和 Agent.ProxyAppId 均必填。
            req.setAgent(agent);
            // 文件夹数组，签署流程总数不能超过50个，一个文件夹下，不能超过20个签署流程
            req.setDownLoadFlows(DownloadFlowInfos);

            // 返回的resp是一个GetDownloadFlowUrlResponse的实例，与请求对象对应
            return client.GetDownloadFlowUrl(req);
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
            // 需要下载的流程Id
            String[] flowIds = new String[]{"*********************"};
            String FileName = "文件夹1";

            DownloadFlowInfo downloadFlowInfo = new DownloadFlowInfo();
            downloadFlowInfo.setFileName(FileName);
            downloadFlowInfo.setFlowIdList(flowIds);


            GetDownloadFlowUrlResponse getDownloadFlowUrlRes = GetDownloadFlowUrl.getDownloadFlowUrl(new DownloadFlowInfo[]{downloadFlowInfo}, agent);
            assert getDownloadFlowUrlRes != null;
            System.out.println(GetDownloadFlowUrlResponse.toJsonString(getDownloadFlowUrlRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}