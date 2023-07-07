package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsResponse;

/**
 * 根据签署流程信息批量获取资源下载链接，可以下载签署中、签署完的合同，需合作企业先进行授权。
 * 此接口直接返回下载的资源的url，与接口GetDownloadFlowUrl跳转到控制台的下载方式不同。
 * 详细参考 https://cloud.tencent.com/document/api/1420/63220
 */
public class DescribeResourceUrlsByFlows {
    /**
     * 根据签署流程信息批量获取资源下载链接
     *
     * @param agent   第三方平台应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return DescribeResourceUrlsByFlowsResponse
     */
    public static DescribeResourceUrlsByFlowsResponse describeResourceUrlsByFlows(Agent agent, String[] flowIds) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeResourceUrlsByFlowsRequest req = new DescribeResourceUrlsByFlowsRequest();

            // 第三方平台应用相关信息。
	        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 必填。
            req.setAgent(agent);
            // 查询资源所对应的签署流程Id，最多支持50个
            req.setFlowIds(flowIds);

            // 返回的resp是一个DescribeResourceUrlsByFlowsResponse的实例，与请求对象对应
            return client.DescribeResourceUrlsByFlows(req);
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
            // 资源所对应的签署流程Id
            String[] flowIds = new String[]{"***************"};

            DescribeResourceUrlsByFlowsResponse urlResp = DescribeResourceUrlsByFlows.
                    describeResourceUrlsByFlows(agent, flowIds);

            assert urlResp != null;
            System.out.println(DescribeResourceUrlsByFlowsResponse.toJsonString(urlResp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}