package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.Agent;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsRequest;
import com.tencentcloudapi.essbasic.v20210526.models.ChannelDescribeOrganizationSealsResponse;

/**
 * 查询渠道子客企业电子印章
 */
public class ChannelDescribeOrganizationSeals {
    /**
     * 查询渠道子客企业电子印章，需要操作者具有管理印章权限
     * 客户指定需要获取的印章数量和偏移量，数量最多100，超过100按100处理；
     * 入参InfoType控制印章是否携带授权人信息，为1则携带，为0则返回的授权人信息为空数组。
     * 接口调用成功返回印章的信息列表还有企业印章的总数。
     *
     * @param agent    渠道应用相关信息
     * @param sealId   印章id（没有输入返回所有）
     * @param infoType 查询信息类型，为0时不返回授权用户，为1时返回
     * @param limit    返回最大数量，最大为100
     * @param offset   偏移量，默认为0，最大为20000
     * @return ChannelDescribeOrganizationSealsResponse
     */
    public static ChannelDescribeOrganizationSealsResponse channelDescribeOrganizationSeals(Agent agent,
                                                                                            String sealId,
                                                                                            Long infoType,
                                                                                            Long offset,
                                                                                            Long limit) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            ChannelDescribeOrganizationSealsRequest req = new ChannelDescribeOrganizationSealsRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 印章id（没有输入返回所有）
            req.setSealId(sealId);
            // 查询信息类型，为0时不返回授权用户，为1时返回
            req.setInfoType(infoType);
            // 返回最大数量，最大为100
            req.setLimit(limit);
            // 偏移量，默认为0，最大为20000
            req.setOffset(offset);

            // 返回的resp是一个ChannelDescribeOrganizationSealsResponse的实例，与请求对象对应
            return client.ChannelDescribeOrganizationSeals(req);
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
            // 设置agent参数
            Agent agent = CreateFlowUtils.setAgent();

            String sealId = "*********************";
            Long infoType = 1L;
            Long limit = 10L;
            Long offset = 0L;

            ChannelDescribeOrganizationSealsResponse ChannelDescribeOrganizationSealsRes =
                    ChannelDescribeOrganizationSeals.channelDescribeOrganizationSeals(agent, sealId, infoType, limit, offset);
            assert ChannelDescribeOrganizationSealsRes != null;
            System.out.println(ChannelDescribeOrganizationSealsResponse.toJsonString(ChannelDescribeOrganizationSealsRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}