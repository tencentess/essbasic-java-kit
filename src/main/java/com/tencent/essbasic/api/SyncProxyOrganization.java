package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationRequest;
import com.tencentcloudapi.essbasic.v20210526.models.SyncProxyOrganizationResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.convertImageFileToBase64;

/**
 * 用于同步渠道子客企业信息，主要是子客企业的营业执照，便于子客企业开通过程中不用手动上传。
 * 若有需要调用此接口，需要在创建控制链接CreateConsoleLoginUrl之后即刻进行调用。
 * 详细参考 https://cloud.tencent.com/document/api/1420/61518
 */
public class SyncProxyOrganization {
    /**
     * 此接口（SyncProxyOrganization）用于同步渠道子客企业信息，主要是子客企业的营业执照，便于子客企业开通过程中不用手动上传。
     * 若有需要调用此接口，需要在创建控制链接CreateConsoleLoginUrl之后即刻进行调用。
     *
     * @param agent                   渠道应用相关信息
     * @param proxyOrganizationName   渠道侧合作企业名称，最大长度64个字符
     * @param businessLicense         营业执照正面照(PNG或JPG) base64格式, 大小不超过5M
     * @param proxyLegalName          渠道侧合作企业法人/负责人姓名
     * @param uniformSocialCreditCode 渠道侧合作企业统一社会信用代码，最大长度200个字符
     * @return SyncProxyOrganizationResponse
     */
    public static SyncProxyOrganizationResponse syncProxyOrganization(Agent agent, String proxyOrganizationName,
                                                                      String businessLicense, String proxyLegalName,
                                                                      String uniformSocialCreditCode) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SyncProxyOrganizationRequest req = new SyncProxyOrganizationRequest();

            // 应用信息
	        // 此接口Agent.AppId、Agent.ProxyOrganizationOpenId必填
            req.setAgent(agent);
            // 渠道侧合作企业名称，最大长度64个字符
            req.setProxyOrganizationName(proxyOrganizationName);
            // 营业执照正面照(PNG或JPG) base64格式, 大小不超过5M
            req.setBusinessLicense(businessLicense);
            // 渠道侧合作企业法人/负责人姓名
            req.setProxyLegalName(proxyLegalName);
            // 渠道侧合作企业统一社会信用代码，最大长度200个字符
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
            // 设置agent参数
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