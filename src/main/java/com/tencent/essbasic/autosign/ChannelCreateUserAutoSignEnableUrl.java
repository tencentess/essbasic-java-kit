package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.AutoSignConfig;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateUserAutoSignEnableUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCreateUserAutoSignEnableUrlResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;


public class ChannelCreateUserAutoSignEnableUrl {

    public static void main(String[] args) {

        ChannelCreateUserAutoSignEnableUrlRequest req = prepareCreateUserAutoSignEnableUrlRequest("");

        AutoSignConfig autoSignConfig = PrepareUserAutoSignConfig(
                PrepareUserThreeFactor("姓名", "身份证号"),
                "https://example.com", true, true, true);

        req.setAutoSignConfig(autoSignConfig);

        // 实例化一个client
        EssbasicClient client = CreateFlowUtils.initClient();

        try {
            ChannelCreateUserAutoSignEnableUrlResponse res = client.ChannelCreateUserAutoSignEnableUrl(req);
            assert res != null;
            System.out.println(ChannelCreateUserAutoSignEnableUrlResponse.toJsonString(res));
            System.out.println("您的开通链接为：" + res.getUrl());
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造用户信息
     *
     * @param name      姓名
     * @param idCardNum 身份证号
     * @return threeFactor 用户信息
     */
    public static UserThreeFactor PrepareUserThreeFactor(String name, String idCardNum) {
        UserThreeFactor threeFactor = new UserThreeFactor();
        // 姓名
        threeFactor.setName(name);
        // 身份证号
        threeFactor.setIdCardNumber(idCardNum);
        threeFactor.setIdCardType("ID_CARD");
        return threeFactor;
    }

    /**
     * 构造自动签配置
     *
     * @param threeFactor          用户身份信息
     * @param callBackUrl          回调地址
     * @param selfDefineSeal       是否允许自定义印章：true-允许
     * @param needSealImgCallback  是否需要回调印章：true-需要
     * @param needCertInfoCallback 是否需要签名证书回调：true-需要
     * @return autoSignConfig 自动签配置
     */
    public static AutoSignConfig PrepareUserAutoSignConfig(
            UserThreeFactor threeFactor,
            String callBackUrl,
            boolean selfDefineSeal,
            boolean needSealImgCallback,
            boolean needCertInfoCallback
    ) {
        AutoSignConfig autoSignConfig = new AutoSignConfig();
        autoSignConfig.setUserInfo(threeFactor);
        // 回调地址
        autoSignConfig.setCallbackUrl(callBackUrl);
        // 是否允许自定义印章：true-允许
        autoSignConfig.setUserDefineSeal(selfDefineSeal);
        // 是否需要回调印章：true-需要
        autoSignConfig.setSealImgCallback(needSealImgCallback);
        // 是否需要签名证书回调：true-需要
        autoSignConfig.setCertInfoCallback(needCertInfoCallback);
        return autoSignConfig;
    }

    /**
     * 构造请求基本参数
     *
     * @param urlType 链接类型：默认-小程序，H5SIGN-h5端链接
     * @return CreateUserAutoSignEnableUrlRequest 请求体
     */
    public static ChannelCreateUserAutoSignEnableUrlRequest prepareCreateUserAutoSignEnableUrlRequest(String urlType) {
        ChannelCreateUserAutoSignEnableUrlRequest req = new ChannelCreateUserAutoSignEnableUrlRequest();
        // 渠道应用相关信息
        req.setAgent(CreateFlowUtils.setAgent());
        // 链接类型：默认-小程序，H5SIGN-h5端链接
        req.setUrlType(urlType);
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        return req;
    }
}


