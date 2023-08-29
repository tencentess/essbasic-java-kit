package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelUserAutoSignEnableUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelUserAutoSignEnableUrlResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;

public class ChannelCancelUserAutoSignEnableUrl {
    public static void main(String[] args) {

        ChannelCancelUserAutoSignEnableUrlRequest req = prepareChannelCancelUserAutoSignEnableUrl(
                "姓名", "身份证号");

        // 实例化一个client
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象

        try {
            // 请求
            ChannelCancelUserAutoSignEnableUrlResponse res = client.ChannelCancelUserAutoSignEnableUrl(req);
            assert res != null;
            System.out.println(ChannelCancelUserAutoSignEnableUrlResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    public static ChannelCancelUserAutoSignEnableUrlRequest prepareChannelCancelUserAutoSignEnableUrl(
            String name, String idCardNumber) {
        ChannelCancelUserAutoSignEnableUrlRequest req = new ChannelCancelUserAutoSignEnableUrlRequest();
        req.setAgent(CreateFlowUtils.setAgent());
        // sceneKey E_PRESCRIPTION_AUTO_SIGN-医疗自动签
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        UserThreeFactor userInfo = ChannelCreateUserAutoSignEnableUrl.PrepareUserThreeFactor(name, idCardNumber);
        req.setUserInfo(userInfo);
        return req;
    }
}
