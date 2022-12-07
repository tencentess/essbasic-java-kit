package com.tencent.essbasic.api;

import com.tencent.essbasic.bytemplate.ByTemplate;
import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import java.util.Arrays;

/**
 * 用于使用多个模板批量创建签署流程。当前可批量发起合同（签署流程）数量最大为20个。
 */

public class CreateFlowsByTemplates {
    /**
     * 用于使用多个模板批量创建签署流程
     *
     * @param agent     渠道应用相关信息
     * @param flowInfos 多个合同（签署流程）信息
     * @return CreateFlowsByTemplatesResponse
     */
    public static CreateFlowsByTemplatesResponse createFlowsByTemplates(Agent agent, FlowInfo[] flowInfos) {
        try {
            // 实例化一个client
            EssbasicClient client = CreateFlowUtils.initClient();
            // 实例化一个请求对象,每个接口都会对应一个request对象
            CreateFlowsByTemplatesRequest req = new CreateFlowsByTemplatesRequest();

            // 渠道应用相关信息
            req.setAgent(agent);
            // 多个合同（签署流程）信息
            req.setFlowInfos(flowInfos);

            // 返回的resp是一个CreateFlowsByTemplatesResponse的实例，与请求对象对应
            return client.CreateFlowsByTemplates(req);
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
            // 模板Id
            String TemplateId = "***************";
            String FlowName = "我的第一份合同";

            // 设置agent参数
            Agent agent = CreateFlowUtils.setAgent();
            // 通过DescribeTemplates接口获得的RecipientId
            DescribeTemplatesResponse describeTemplatesResponse = DescribeTemplates.describeTemplates(agent, TemplateId);


            FlowInfo[] FlowInfos = new FlowInfo[1];

            // 定义签署人
            assert describeTemplatesResponse != null;
            FlowApproverInfo[] flowApproverInfos = ByTemplate.BuildApprovers(
                    Arrays.asList(describeTemplatesResponse.getTemplates()[0].getRecipients()));
            FlowInfos[0] = CreateFlowUtils.fillFlowInfo(TemplateId, FlowName, flowApproverInfos);


            CreateFlowsByTemplatesResponse flowResponse = CreateFlowsByTemplates.createFlowsByTemplates(agent, FlowInfos);
            assert flowResponse != null;
            System.out.println(CreateFlowsByTemplatesResponse.toJsonString(flowResponse));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}