package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSealByImageRequest;
import com.tencentcloudapi.essbasic.v20210526.models.CreateSealByImageResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.convertImageFileToBase64;

/**
 * 渠道通过图片为子客代创建印章，图片最大5m；此接口不可直接使用，需要运营申请
 */
public class CreateSealByImage {
    /**
     * 渠道通过图片为子客代创建印章，图片最大5m；此接口不可直接使用，需要运营申请
     *
     * @param agent     渠道应用相关信息
     * @param sealName  印章名称，最大长度不超过30字符
     * @param sealImage 印章图片base64
     * @return CreateSealByImageResponse
     */
    public static CreateSealByImageResponse createSealByImage(String sealName, String sealImage, Agent agent) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateSealByImageRequest req = new CreateSealByImageRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 印章名称，最大长度不超过30字符
            req.setSealName(sealName);

            // 印章图片base64
            req.setSealImage(sealImage);

            // 返回的resp是一个CreateSealByImageResponse的实例，与请求对象对应
            return client.CreateSealByImage(req);
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
            String sealName = "xxx";
            String filePath = "test_seal.png";
            String sealImage = convertImageFileToBase64(filePath);

            CreateSealByImageResponse createSealByImageRes = CreateSealByImage.createSealByImage(sealName, sealImage, agent);
            assert createSealByImageRes != null;
            System.out.println(CreateSealByImageResponse.toJsonString(createSealByImageRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}