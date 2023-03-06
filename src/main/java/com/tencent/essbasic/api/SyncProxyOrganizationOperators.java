package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ProxyOrganizationOperator;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationOperatorsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationOperatorsResponse;

/**
 * 用于同步子客企业经办人列表，主要是同步经办人的离职状态。
 * 子客Web控制台的组织架构管理，是依赖于平台企业的，无法针对员工做新增/更新/离职等操作。
 * 若经办人信息有误，或者需要修改，也可以先将之前的经办人做离职操作，然后重新使用控制台链接CreateConsoleLoginUrl让经办人重新实名。
 * 详细参考 https://cloud.tencent.com/document/api/1420/61517
 */
public class SyncProxyOrganizationOperators {
    /**
     * 此接口（SyncProxyOrganizationOperators）用于同步子客企业经办人列表，主要是同步经办人的离职状态。
     * 子客Web控制台的组织架构管理，是依赖于平台企业的，无法针对员工做新增/更新/离职等操作。
     * 若经办人信息有误，或者需要修改，也可以先将之前的经办人做离职操作，然后重新使用控制台链接CreateConsoleLoginUrl让经办人重新实名。
     *
     * @param agent                      第三方平台应用相关信息
     * @param operatorType               操作类型，新增:"CREATE"，修改:"UPDATE"，离职:"RESIGN"
     * @param proxyOrganizationOperators 经办人信息列表，最大长度200
     * @return SyncProxyOrganizationOperatorsResponse
     */
    public static SyncProxyOrganizationOperatorsResponse syncProxyOrganizationOperators(Agent agent,
                                                                                        String operatorType,
                                                                                        ProxyOrganizationOperator[] proxyOrganizationOperators) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SyncProxyOrganizationOperatorsRequest req = new SyncProxyOrganizationOperatorsRequest();

            // 第三方平台应用相关信息。 
	        // 此接口Agent.AppId 和 Agent.ProxyOrganizationOpenId必填。
            req.setAgent(agent);
            // 操作类型，新增:"CREATE"，修改:"UPDATE"，离职:"RESIGN"
            req.setOperatorType(operatorType);
            // 经办人信息列表，最大长度200
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
            // 设置agent参数
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