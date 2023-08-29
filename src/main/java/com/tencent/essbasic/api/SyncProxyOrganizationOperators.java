package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ProxyOrganizationOperator;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationOperatorsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationOperatorsResponse;

public class SyncProxyOrganizationOperators {

    public static SyncProxyOrganizationOperatorsResponse syncProxyOrganizationOperators(Agent agent,
                                                                                        String operatorType,
                                                                                        ProxyOrganizationOperator[] proxyOrganizationOperators) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SyncProxyOrganizationOperatorsRequest req = new SyncProxyOrganizationOperatorsRequest();

            req.setAgent(agent);

            req.setOperatorType(operatorType);

            req.setProxyOrganizationOperators(proxyOrganizationOperators);

            // 返回的resp是一个SyncProxyOrganizationOperatorsResponse的实例，与请求对象对应
            return client.SyncProxyOrganizationOperators(req);
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

            String operatorType = "CREATE";

            ProxyOrganizationOperator operator = new ProxyOrganizationOperator();
            operator.setId("*********************");
            operator.setName("*********************");
            operator.setMobile("*********************");
            operator.setIdCardNumber("*********************");
            operator.setIdCardType("*********************");
            ProxyOrganizationOperator[] proxyOrganizationOperators = {operator};

            SyncProxyOrganizationOperatorsResponse SyncProxyOrganizationOperatorsRes = SyncProxyOrganizationOperators.
                    syncProxyOrganizationOperators(agent, operatorType, proxyOrganizationOperators);
            assert SyncProxyOrganizationOperatorsRes != null;
            System.out.println(SyncProxyOrganizationOperatorsResponse.toJsonString(SyncProxyOrganizationOperatorsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}