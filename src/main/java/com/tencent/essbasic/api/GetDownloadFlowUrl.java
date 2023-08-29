package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DownloadFlowInfo;
import com.tencentcloudapi.essbasic.v20210526.models.GetDownloadFlowUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.GetDownloadFlowUrlResponse;

public class GetDownloadFlowUrl {

    public static GetDownloadFlowUrlResponse getDownloadFlowUrl(DownloadFlowInfo[] DownloadFlowInfos, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            GetDownloadFlowUrlRequest req = new GetDownloadFlowUrlRequest();

            req.setAgent(agent);

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

            Agent agent = CreateFlowUtils.setAgent();

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