package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

public class ChannelCreatePreparedPersonalEsign {
    public static void main(String[] args) {

        ChannelCreatePreparedPersonalEsignRequest req = prepareChannelCancelUserAutoSignEnableUrl(
                "姓名", "身份证号", "印章图片base64", "印章名称");

        // 实例化一个client
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象

        try {
            // 请求
            ChannelCreatePreparedPersonalEsignResponse res = client.ChannelCreatePreparedPersonalEsign(req);
            assert res != null;
            System.out.println(ChannelCreatePreparedPersonalEsignResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    public static ChannelCreatePreparedPersonalEsignRequest prepareChannelCancelUserAutoSignEnableUrl(
            String name, String idCardNumber, String sealImgBase64, String sealName) {
        ChannelCreatePreparedPersonalEsignRequest req = new ChannelCreatePreparedPersonalEsignRequest();
        req.setAgent(CreateFlowUtils.setAgent());

        req.setUserName(name);
        req.setIdCardType("ID_CARD");
        req.setIdCardNumber(idCardNumber);
        req.setSealImage(sealImgBase64);
        req.setSealName(sealName);
        return req;
    }
}
