package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelUserAutoSignEnableUrlRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelCancelUserAutoSignEnableUrlResponse;
import com.tencentcloudapi.essbasic.v20210526.models.UserThreeFactor;

/**
 * 撤销自动签开通链接
 * 此接口（ChannelCancelUserAutoSignEnableUrl）用来撤销发送给个人用户的自动签开通链接，
 * 撤销后对应的个人用户开通链接失效。若个人用户已经完成开通，将无法撤销。（处方单场景专用，使用此接口请与客户经理确认）
 * <p>
 * 接口文档
 * https://qian.tencent.com/developers/partnerApis/enterpriseUsers/ChannelCancelUserAutoSignEnableUrl
 */
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

    /**
     * 构造请求
     *
     * @param name         姓名
     * @param idCardNumber 身份证号码
     * @return ChannelCancelUserAutoSignEnableUrlRequest 请求体
     */
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
