package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationRequest;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.convertImageFileToBase64;

public class SyncProxyOrganization {

    public static SyncProxyOrganizationResponse syncProxyOrganization(Agent agent, String proxyOrganizationName,
                                                                      String businessLicense, String proxyLegalName,
                                                                      String uniformSocialCreditCode) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SyncProxyOrganizationRequest req = new SyncProxyOrganizationRequest();

            req.setAgent(agent);

            req.setProxyOrganizationName(proxyOrganizationName);

            req.setBusinessLicense(businessLicense);

            req.setProxyLegalName(proxyLegalName);

            req.setUniformSocialCreditCode(uniformSocialCreditCode);

            // 返回的resp是一个SyncProxyOrganizationResponse的实例，与请求对象对应
            return client.SyncProxyOrganization(req);
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

            String proxyOrganizationName = "*********************";

            String filePath = "test_businessLicense.png";
            String businessLicense = convertImageFileToBase64(filePath);

            String proxyLegalName = "*********************";
            String uniformSocialCreditCode = "*********************";

            SyncProxyOrganizationResponse SyncProxyOrganizationRes = SyncProxyOrganization.syncProxyOrganization(agent,
                    proxyOrganizationName, businessLicense, proxyLegalName, uniformSocialCreditCode);
            assert SyncProxyOrganizationRes != null;
            System.out.println(SyncProxyOrganizationResponse.toJsonString(SyncProxyOrganizationRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}