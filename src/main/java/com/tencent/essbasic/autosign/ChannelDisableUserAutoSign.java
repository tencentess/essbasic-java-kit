package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDisableUserAutoSignRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDisableUserAutoSignResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;


public class ChannelDisableUserAutoSign {

    public static void main(String[] args) {
        ChannelDisableUserAutoSignRequest req = prepareChannelDisableUserAutoSignRequest(
                "姓名", "身份证号");

        // 实例化一个client
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象

        try {
            // 请求
            ChannelDisableUserAutoSignResponse res = client.ChannelDisableUserAutoSign(req);
            assert res != null;
            System.out.println(ChannelDisableUserAutoSignResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    public static ChannelDisableUserAutoSignRequest prepareChannelDisableUserAutoSignRequest(
            String name, String idCardNumber) {
        ChannelDisableUserAutoSignRequest req = new ChannelDisableUserAutoSignRequest();
        req.setAgent(CreateFlowUtils.setAgent());
        // sceneKey E_PRESCRIPTION_AUTO_SIGN-医疗自动签
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        UserThreeFactor userInfo = ChannelCreateUserAutoSignEnableUrl.PrepareUserThreeFactor(name, idCardNumber);
        req.setUserInfo(userInfo);
        return req;
    }
}
