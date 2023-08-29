package com.tencent.essbasic.autosign;

import com.tencent.essbasic.api.DescribeFileUrls;
import com.tencent.essbasic.api.UploadFiles;
import com.tencent.essbasic.common.CreateFlowUtils;
import com.tencent.essbasic.config.Config;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.essbasic.v20210526.EssbasicClient;
import com.tencentcloudapi.essbasic.v20210526.models.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;


public class CreateFlowsByFileForAutoSignQuickStart {
    public static void main(String[] args) {

        // Step 1
        // 定义文件所在的路径
        String inputFilePath = "medical.pdf";
        // 定义合同名
        String flowName = "医疗自动签署测试合同-" + System.currentTimeMillis();

        // 构造自动签签署人
        // 此块代码中的$approvers仅用于快速发起一份合同样例，非正式对接用
        // 医生的信息
        // 个人签署方的姓名，必须是真实的才能正常签署
        FlowApproverInfo personApprover1 = BuildAutoSignPersonApprover(
                "医生姓名", "医生手机号", "医生身份证号",
                "医生", 20F, 0F, 100F, 100L);

        // 药师的信息
        FlowApproverInfo personApprover2 = BuildAutoSignPersonApprover(
                "药师姓名", "药师手机号", "药师身份证号",
                "药师", 20F, 0F, 100F, 100L);

        // 平台静默签署
        FlowApproverInfo serverApprover = BuildServerSignApprover(Config.ServerSignSealId, Config.ServerSignOrgName);

        FlowApproverInfo[] allApprovers = new FlowApproverInfo[]{personApprover1, personApprover2, serverApprover};

        // Step 2
        // 使用文件发起合同
        // 发起合同
        String fileBase64 = convertImageFileToBase64(inputFilePath);
        try {
            String flowId = AutoSignCreateFlowByFileDirectly(flowName, fileBase64, allApprovers);
            System.out.println("您创建的合同id为: ");
            System.out.println(flowId);

            // Step 3
            // 下载合同
            String url = DescribeFileUrls.describeFileUrls(flowId);
            // 返回合同下载链接
            System.out.println("请访问以下地址下载您的合同：");
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FlowApproverInfo BuildAutoSignPersonApprover(String name, String mobile, String idCardNum,
                                                               String keyWord, float offSetX, float offsetY,
                                                               float width, float height) {
        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        flowApproverInfo.setApproverType("PERSON_AUTO_SIGN");

        flowApproverInfo.setName(name);

        flowApproverInfo.setMobile(mobile);

        flowApproverInfo.setIdCardNumber(idCardNum);
        flowApproverInfo.setIdCardType("ID_CARD");

        flowApproverInfo.setApproverNeedSignReview(false);

        // 签署人对应的签署控件
        Component component = BuildKeyWordComponent(keyWord, "Right", offSetX, offsetY, width, height);
        flowApproverInfo.setSignComponents(new Component[]{component});

        return flowApproverInfo;
    }


    private static FlowApproverInfo BuildServerSignApprover(String serverSignSealId, String orgName) {
        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo serverSignApprover = new FlowApproverInfo();

        serverSignApprover.setApproverType("ENTERPRISESERVER");

        serverSignApprover.setOrganizationName(orgName);
        // 签署人对应的签署控件
        Component component = BuildComponent("SIGN_SEAL",
                serverSignSealId, 200F, 200F, 150F, 200F,
                0L, 1L);

        // 本环节操作人签署控件配置，为企业静默签署时，只允许类型为SIGN_SEAL（印章）和SIGN_DATE（日期）控件，并且传入印章编号
        serverSignApprover.setSignComponents(new Component[]{component});
        return serverSignApprover;
    }

    /**
     * 一键使用文件发起流程
     *
     * @param flowName      流程名
     * @param fileBase64    文件路径
     * @param approverInfos 签署者列表ApproverInfo[]
     * @return flowID
     */
    public static String AutoSignCreateFlowByFileDirectly(String flowName, String fileBase64,
                                                          FlowApproverInfo[] approverInfos) throws Exception {

        // 设置uploadFile参数,这里可以修改传入数量
        UploadFile[] uploadFiles = new UploadFile[]{new UploadFile()};
        uploadFiles[0].setFileBody(fileBase64);

        UploadFilesResponse uploadRes = UploadFiles.uploadFiles(CreateFlowUtils.setAgent(), uploadFiles);
        // fileId
        assert uploadRes != null;
        String fileId = uploadRes.getFileIds()[0];
        return AutoSignCreateFlowByFiles(flowName, approverInfos, fileId);
    }

    public static String AutoSignCreateFlowByFiles(String flowName, FlowApproverInfo[] approvers,
                                                   String fileId) throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象
        ChannelCreateFlowByFilesRequest req = new ChannelCreateFlowByFilesRequest();

        req.setAgent(CreateFlowUtils.setAgent());

        req.setFlowApprovers(approvers);

        req.setFlowName(flowName);

        req.setFileIds(new String[]{fileId});

        req.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");

        req.setUnordered(true);

        req.setNeedSignReview(false);

        ChannelCreateFlowByFilesResponse response = client.ChannelCreateFlowByFiles(req);
        assert response != null;
        return response.getFlowId();
    }


    public static Component BuildKeyWordComponent(String componentId, String relativeLocation,
                                                  float offSetX, float offSetY, float componentWidth,
                                                  float componentHeight) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        // ComponentId 关键字
        component.setComponentId(componentId);

        component.setComponentType("SIGN_SIGNATURE");

        component.setComponentWidth(componentWidth);

        component.setComponentHeight(componentHeight);

        component.setFileIndex(0L);

        component.setComponentPage(1L);

        component.setGenerateMode("KEYWORD");
 
        component.setOffsetX(offSetX);

        component.setOffsetY(offSetY);

        component.setRelativeLocation(relativeLocation);

        return component;
    }

    public static Component BuildComponent(String componentType, String componentValue, float componentPosX,
                                           float componentPosY, float componentWidth, float componentHeight,
                                           long fileIndex, long componentPage) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        component.setComponentType(componentType);

        component.setComponentValue(componentValue);

        component.setComponentPosX(componentPosX);

        component.setComponentPosY(componentPosY);

        component.setComponentWidth(componentWidth);

        component.setComponentHeight(componentHeight);

        component.setFileIndex(fileIndex);

        component.setComponentPage(componentPage);

        return component;
    }

    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @param filePath 文件路径
     * @return FlowInfo
     */
    public static String convertImageFileToBase64(String filePath) {
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        URL url = CreateFlowUtils.class.getClassLoader().getResource(filePath);
        try {
            assert url != null;
            inputStream = new FileInputStream(url.getFile());
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(buffer);
    }
}
