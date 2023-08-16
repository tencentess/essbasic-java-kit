package com.tencent.essbasic.autosign;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

/**
 * 创建导入个人印章
 * 此接口（ChannelCreatePreparedPersonalEsign）用于创建导入个人印章
 * 此接口也可开通个人自动签署（处方单场景专用，使用此接口请与客户经理确认）
 * <p>
 * 接口文档
 * https://qian.tencent.com/developers/partnerApis/seals/ChannelCreatePreparedPersonalEsign
 */
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

    /**
     * 构造请求
     *
     * @param name          姓名
     * @param idCardNumber  身份证号
     * @param sealImgBase64 印章图片base64
     * @param sealName      印章名称
     * @return ChannelCreatePreparedPersonalEsignRequest
     */
    public static ChannelCreatePreparedPersonalEsignRequest prepareChannelCancelUserAutoSignEnableUrl(
            String name, String idCardNumber, String sealImgBase64, String sealName) {
        ChannelCreatePreparedPersonalEsignRequest req = new ChannelCreatePreparedPersonalEsignRequest();
        req.setAgent(CreateFlowUtils.setAgent());

        req.setUserName(name);
        req.setIdCardType("ID_CARD");
        req.setIdCardNumber(idCardNumber);
        req.setSealImage(sealImgBase64);
        req.setSealName(sealName);
        // 如果需要开通个人自动签署, 要设置以下参数, 该功能需联系运营工作人员开通后使用
//        req.setMobile("手机号");
//        req.setEnableAutoSign(true);
        return req;
    }
}
