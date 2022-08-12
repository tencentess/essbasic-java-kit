package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 此接口用于查询合同(签署流程)的详细信息。
 */

public class DescribeFlowDetailInfo {
    /**
     * 查询合同(签署流程)的详细信息
     *
     * @param agent   渠道应用相关信息
     * @param flowIds 资源所对应的签署流程Id
     * @return DescribeFlowDetailInfoResponse
     */
    public static DescribeFlowDetailInfoResponse describeFlowDetailInfo(Agent agent, String[] flowIds) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DescribeFlowDetailInfoRequest req = new DescribeFlowDetailInfoRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 资源所对应的签署流程Id
            req.setFlowIds(flowIds);

            // 返回的resp是一个DescribeFlowDetailInfoResponse的实例，与请求对象对应
            DescribeFlowDetailInfoResponse resp = client.DescribeFlowDetailInfo(req);
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

            DescribeFlowDetailInfoResponse resp = describeFlowDetailInfo(agent, flowIds);

            // 输出json格式的字符串回包
            assert resp != null;
            System.out.println(DescribeFlowDetailInfoResponse.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}