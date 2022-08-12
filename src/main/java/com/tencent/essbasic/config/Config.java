package com.tencent.essbasic.config;

/**
 * 基础配置，调用API之前必须填充的参数
 */
public class Config {
    // 腾讯云ak/sk (secretId/secretKey) 调用API的密钥对，通过腾讯云后台CAM控制台获取
    public static final String SecretId = "****************";
    public static final String SecretKey = "****************";

    // AppId
    public static final String AppId = "****************";

    // 腾讯电子签颁发给渠道侧合作企业的应用ID
    public static final String ProxyAppId = "****************";

    // 渠道/平台合作企业的企业ID
    public static final String ProxyOrganizationOpenId = "****************";

    // 渠道/平台合作企业经办人（操作员）ID
    public static final String ProxyOperatorOpenId = "****************";

    // 企业方静默签用的印章Id，电子签控制台获取
    public static String ServerSignSealId = "****************";

    // API域名，现网使用 ess.tencentcloudapi.com
    public static String EndPoint = "essbasic.test.ess.tencent.cn";

    // 文件服务域名，现网使用 file.ess.tencent.cn
    public static String FileServiceEndPoint = "file.test.ess.tencent.cn";

    // 模板Id，填写自己需要的模板
    public static final String TemplateId = "****************";

    // 合同发起数量,可以修改
    public static final int COUNT = 1;
}
