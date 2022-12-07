package com.tencent.essbasic.api;

import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.DescribeResourceUrlsByFlowsResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserInfo;

/**
 * 查询文件下载URL
 * 适用场景：通过传参合同流程编号，下载对应的合同PDF文件流到本地。
 */

public class DescribeFileUrls {
    /**
     * 下载对应的合同PDF文件流到本地
     *
     * @param flowId 资源所对应的签署流程Id
     * @return describeFileUrls
     */
    public static String describeFileUrls(String flowId) {

        // 设置agent参数
        Agent agent = new Agent();
        agent.setAppId(Config.AppId);
        agent.setProxyAppId(Config.ProxyAppId);
        agent.setProxyOrganizationOpenId(Config.ProxyOrganizationOpenId);
        UserInfo proxyOperator = new UserInfo();
        proxyOperator.setOpenId(Config.ProxyOperatorOpenId);
        agent.setProxyOperator(proxyOperator);

        String[] flowIds = new String[1];
        flowIds[0] = flowId;
        DescribeResourceUrlsByFlowsResponse urlResp = DescribeResourceUrlsByFlows.
                describeResourceUrlsByFlows(agent, flowIds);

        assert urlResp != null;
        return urlResp.getFlowResourceUrlInfos()[0].getResourceUrlInfos()[0].getUrl();
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        try {
            // 资源所对应的签署流程Id
            String flowId = "*************";

            String url = describeFileUrls(flowId);
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
