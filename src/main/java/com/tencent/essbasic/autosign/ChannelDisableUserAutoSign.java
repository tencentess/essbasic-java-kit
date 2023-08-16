package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDisableUserAutoSignRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDisableUserAutoSignResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;


/**
 * 关闭个人自动签功能
 * 企业方可以通过此接口关闭个人的自动签功能
 * <p>
 * 接口名称
 * ChannelDisableUserAutoSign
 * <p>
 * 接口文档
 * https://qian.tencent.com/developers/partnerApis/enterpriseUsers/ChannelDisableUserAutoSign
 */
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

    /**
     * 构造请求基本参数
     *
     * @param name         姓名
     * @param idCardNumber 身份证号码
     * @return ChannelDisableUserAutoSignRequest 请求体
     */
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
