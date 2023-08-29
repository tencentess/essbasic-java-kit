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

public class UploadFiles {

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


            req.setAgent(agent);

            req.setFileInfos(uploadFile);

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

            Agent agent = setAgent();
            String filePath = "blank.pdf";
            String fileBase64 = convertImageFileToBase64(filePath);

            UploadFile[] uploadFiles = new UploadFile[]{new UploadFile()};
            uploadFiles[0].setFileBody(fileBase64);

            UploadFilesResponse uploadRes = UploadFiles.uploadFiles(agent, uploadFiles);
            assert uploadRes != null;
            System.out.println(UploadFilesResponse.toJsonString(uploadRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
