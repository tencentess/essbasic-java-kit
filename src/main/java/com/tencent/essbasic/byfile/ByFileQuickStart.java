package com.tencent.essbasic.byfile;

import com.tencent.essbasic.api.CreateFlowByFileDirectly;
import com.tencent.essbasic.api.DescribeFileUrls;
import com.tencentcloudapi.essbasic.v20210526.models.FlowApproverInfo;
import java.util.Map;

import static com.tencent.essbasic.common.CreateFlowUtils.convertImageFileToBase64;

/**
 * 使用文件发起合同QuickStart
 */
public class ByFileQuickStart {

    /**
     * ByFileQuickStart
     */
    public static void main(String[] args) {
        try {
            // Step 1
            // 定义文件所在的路径
            String filePath = "blank.pdf";
            // 定义合同名
            String flowName = "我的第一个合同";

            // 此处为快速发起；如果是正式接入，构造签署人，请参考函数内说明，构造需要的场景参数
            FlowApproverInfo[] flowApproverInfos = ByFile.BuildApprovers();

            // Step 2
            // 将文件处理为Base64编码后的文件内容
            String fileBase64 = convertImageFileToBase64(filePath);

            // 发起合同
            Map<String, String> resp = CreateFlowByFileDirectly.createFlowByFileDirectly
                    (fileBase64, flowApproverInfos, flowName);

            // 返回合同Id
            System.out.println("您创建的合同id为：");
            System.out.println(resp.get("flowId"));
            System.out.println("\r\n\r\n");
            // 返回签署的链接
            System.out.println("签署链接为：");
            System.out.println(resp.get("url"));
            System.out.println("\r\n\r\n");
            // Step 3
            // 下载合同
            // 返回合同下载链接
            String url = DescribeFileUrls.describeFileUrls(resp.get("flowId"));
            System.out.println("请访问以下地址下载您的合同：");
            System.out.println(url);
            System.out.println("\r\n\r\n");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
