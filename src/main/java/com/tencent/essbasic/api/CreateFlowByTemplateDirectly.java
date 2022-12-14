package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import java.util.HashMap;
import java.util.Map;

import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 通过合同名和模板Id直接发起签署流程
 * 本接口是对于发起合同几个接口的封装，详细参数需要根据自身业务进行调整
 * CreateFlowsByTemplates--CreateSignUrls
 */
public class CreateFlowByTemplateDirectly {
    /**
     * 通过文件base64直接发起签署流程
     *
     * @param flowName   签署流程名称
     * @param templateId 模板唯一标识
     * @return Map<String, String [ ]>
     */
    public static Map<String, String[]> createFlowByTemplateDirectly(String flowName,
                                                                     String templateId,
                                                                     FlowApproverInfo[] flowApproverInfos) {
        Map<String, String[]> resp = new HashMap<>();
        // 设置agent参数
        Agent agent = setAgent();
        // 创建签署流程
        // 签署数量
        int count = Config.COUNT;
        FlowInfo[] FlowInfos = new FlowInfo[count];
        for (int i = 0; i < count; i++) {
            // 构建内容控件填充结构(根据自己需求使用)
            //FlowInfos[i].setFormFields(new FormField[]{BuildFormField("姓名", "张三")});
            FlowInfos[i] = CreateFlowUtils.fillFlowInfo(templateId, flowName, flowApproverInfos);
        }

        // 发起签署
        CreateFlowsByTemplatesResponse flowResponse = CreateFlowsByTemplates.createFlowsByTemplates(agent, FlowInfos);
        assert flowResponse != null;
        String[] FlowIds = flowResponse.getFlowIds();
        resp.put("FlowIds", FlowIds);

        // 获取签署链接
        CreateSignUrlsResponse createSignUrlsRes = CreateSignUrls.createSignUrls(FlowIds, agent);
        assert createSignUrlsRes != null;
        if (createSignUrlsRes.getSignUrlInfos().length != 0) {
            String[] Urls = new String[count];
            for (int i = 0; i < count; i++) {
                Urls[i] = createSignUrlsRes.getSignUrlInfos()[i].getSignUrl();
            }
            resp.put("Urls", Urls);
        }

        return resp;
    }
}
