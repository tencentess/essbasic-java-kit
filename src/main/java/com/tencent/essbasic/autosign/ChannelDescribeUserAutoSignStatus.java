package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeUserAutoSignStatusRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeUserAutoSignStatusResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;

public class ChannelDescribeUserAutoSignStatus {
    public static void main(String[] args) {

        ChannelDescribeUserAutoSignStatusRequest req = prepareChannelDescribeUserAutoSignStatusRequest(
                "姓名", "身份证号");

        // 实例化一个client
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象

        try {
            // 请求
            ChannelDescribeUserAutoSignStatusResponse res = client.ChannelDescribeUserAutoSignStatus(req);
            assert res != null;
            System.out.println(ChannelDescribeUserAutoSignStatusResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    public static ChannelDescribeUserAutoSignStatusRequest prepareChannelDescribeUserAutoSignStatusRequest(
            String name, String idCardNumber) {
        ChannelDescribeUserAutoSignStatusRequest req = new ChannelDescribeUserAutoSignStatusRequest();
        req.setAgent(CreateFlowUtils.setAgent());
        // sceneKey E_PRESCRIPTION_AUTO_SIGN-医疗自动签
        req.setSceneKey("E_PRESCRIPTION_AUTO_SIGN");
        UserThreeFactor userInfo = ChannelCreateUserAutoSignEnableUrl.PrepareUserThreeFactor(name, idCardNumber);
        req.setUserInfo(userInfo);
        return req;
    }
}
