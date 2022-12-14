package com.tencent.essbasic.api;

import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFile;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFilesRequest;
import com.tencentcloudapi.essbasic.v20210526.models.UploadFilesResponse;

import static com.tencent.essbasic.common.CreateFlowUtils.convertImageFileToBase64;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * UploadFiles 用于生成pdf资源编号（FileIds）来配合“用PDF创建流程”接口使用，使用场景可详见“用PDF创建流程”接口说明。
 * 调用时需要设置Domain, 正式环境为 file.ess.tencent.cn
 * 测试环境为 https://file.test.ess.tencent.cn
 * 详细参考 https://cloud.tencent.com/document/api/1420/71479
 */
public class UploadFiles {
    /**
     * 用于生成pdf资源编号
     *
     * @param agent      渠道应用相关信息
     * @param uploadFile 文件内容
     * @return UploadFilesResponse
     */
    public static UploadFilesResponse uploadFiles(Agent agent, UploadFile[] uploadFile) {
        try {
            // 实例化一个client
            Credential cred = new Credential(Config.SecretId, Config.SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(Config.FileServiceEndPoint);
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            EssbasicClient client = new EssbasicClient(cred, "", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            UploadFilesRequest req = new UploadFilesRequest();

            // 应用相关信息，appid 和proxyappid 必填
            req.setAgent(agent);
            // 上传文件内容数组，最多支持20个文件
            req.setFileInfos(uploadFile);
            // 文件对应业务类型
	        // 1. TEMPLATE - 模板； 文件类型：.pdf/.doc/.docx/.html
	        // 2. DOCUMENT - 签署过程及签署后的合同文档/图片控件 文件类型：.pdf/.doc/.docx/.jpg/.png/.xls.xlsx/.html
            req.setBusinessType("DOCUMENT");

            // 返回的resp是一个UploadFilesResponse的实例，与请求对象对应
            return client.UploadFiles(req);
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
            Agent agent = setAgent();
            String filePath = "blank.pdf";
            String fileBase64 = convertImageFileToBase64(filePath);
            // 设置uploadFile参数,这里可以修改传入数量
            UploadFile[] uploadFiles = new UploadFile[]{new UploadFile()};
            uploadFiles[0].setFileBody(fileBase64);
            // 上传文件获取fileId
            UploadFilesResponse uploadRes = UploadFiles.uploadFiles(agent, uploadFiles);
            assert uploadRes != null;
            System.out.println(UploadFilesResponse.toJsonString(uploadRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
