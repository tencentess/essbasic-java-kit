package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 根据签署流程信息批量获取资源下载链接，需合作企业先进行授权
 */
public class DescribeResourceUrlsByFlows {
    /**
     * 根据签署流程信息批量获取资源下载链接
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return DescribeResourceUrlsByFlowsResponse
     */
    public static DescribeResourceUrlsByFlowsResponse describeResourceUrlsByFlows(Agent agent, String[] flowIds) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeResourceUrlsByFlowsRequest req = new DescribeResourceUrlsByFlowsRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
            req.setFlowIds(flowIds);

            // 返回的resp是一个DescribeResourceUrlsByFlowsResponse的实例，与请求对象对应
            DescribeResourceUrlsByFlowsResponse resp = client.DescribeResourceUrlsByFlows(req);
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