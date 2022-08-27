package com.tencent.essbasic.api;

import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencentcloudapi.essbasic.v20210526.models.*;
import com.tencent.essbasic.bytemplate.ByTemplate;
import com.tencent.essbasic.config.Config;

import java.util.*;

import static com.tencent.essbasic.bytemplate.ByTemplate.BuildFormField;
import static com.tencent.essbasic.common.CreateFlowUtils.setAgent;

/**
 * 通过合同名和模板Id直接发起签署流程
 */
public class CreateFlowByTemplateDirectly {
    /**
     * 通过文件base64直接发起签署流程
     *
     * @param flowName    签署流程名称
     * @param templateId 模板唯一标识
     * @return Map<String, String[]>
     */
    public static Map<String, String[]> createFlowByTemplateDirectly(String flowName,
                                                                     String templateId,
                                                                     FlowApproverInfo[] flowApproverInfos){
        Map<String, String[]> resp = new HashMap<>();
        // 设置agent参数
        Agent agent = setAgent();
        // 创建签署流程
        // 签署数量
        int count  = Config.COUNT;
        FlowInfo[] FlowInfos = new FlowInfo[count];
        for (int i = 0; i < count; i++){
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
        if(createSignUrlsRes.getSignUrlInfos().length != 0){
            String[] Urls = new String[count];
            for(int i = 0; i < count; i++){
                Urls[i] = createSignUrlsRes.getSignUrlInfos()[i].getSignUrl();
            }
            resp.put("Urls", Urls);
        }

        return resp;
    }
}
