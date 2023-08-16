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


/**
 * 使用文件发起合同QuickStart 医疗自动签专用 B2CC,
 * <p>
 * !!! 注意这里需要联系客户经理开通文件发起允许静默签白名单 !!!
 * <p>
 * 关键字定位签署坐标（请根据实际PDF中的文件调整关键字）
 * <p>
 * 文档地址：https://qian.tencent.com/developers/partnerApis/startFlows/ChannelCreateFlowByFiles
 * <p>
 * 此接口（ChannelCreateFlowByFiles）用来通过上传后的pdf资源编号来创建待签署的合同流程。
 * 适用场景1：适用非制式的合同文件签署。一般开发者自己有完整的签署文件，可以通过该接口传入完整的PDF文件及流程信息生成待签署的合同流程。
 * 适用场景2：可通过该接口传入制式合同文件，同时在指定位置添加签署控件。可以起到接口创建临时模板的效果。如果是标准的制式文件，建议使用模板功能生成模板ID进行合同流程的生成。
 * 注意事项：该接口需要依赖“多文件上传”接口生成pdf资源编号（FileIds）进行使用。
 * <p>
 * 本示例用于第三方应用集成接口对接，通过文件快速发起第一份合同
 * 建议配合文档进行操作，先修改config里的基本参数以及对应环境域名，然后跑一次
 * 第三方应用集成主要针对平台企业-代理子客发起合同，简要步骤主要是
 * 1. 通过CreateConsoleLoginUrl引导子客企业完成电子签的实名认证 - 子客企业在电子签配置印章等
 * 2. 通过简单封装的CreateFlowByFileDirectly接口上传文件并快速发起一份合同，并得到签署链接
 * 3. 在小程序签署合同，通过API下载合同
 * 基于具体业务上的参数调用，可以参考官网的接口说明
 * https://cloud.tencent.com/document/product/1420/61534
 * 每个API的封装在api目录下可以自己配合相关参数进行调用
 */
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

    /**
     * 打包个人自动签签署方参与者信息
     *
     * @param name      姓名
     * @param mobile    手机号
     * @param idCardNum 身份证号
     * @param keyWord   关键字
     * @param offSetX   偏移坐标:X
     * @param offsetY   偏移坐标:Y
     * @param width     控件宽度
     * @param height    控件长度
     * @return FlowApproverInfo 参与人
     */
    public static FlowApproverInfo BuildAutoSignPersonApprover(String name, String mobile, String idCardNum,
                                                               String keyWord, float offSetX, float offsetY,
                                                               float width, float height) {
        // 签署参与者信息
        // 个人签署方
        FlowApproverInfo flowApproverInfo = new FlowApproverInfo();

        // 签署人类型
        // PERSON-个人/自然人
        // PERSON_AUTO_SIGN-个人自动签（定制化场景下使用）
        // ORGANIZATION-企业（企业签署方或模板发起时的企业静默签）
        // ENTERPRISESERVER-企业静默签（文件发起时的企业静默签字）
        flowApproverInfo.setApproverType("PERSON_AUTO_SIGN");
        // 签署人姓名，最大长度50个字符
        flowApproverInfo.setName(name);
        // 签署人手机号，脱敏显示。大陆手机号为11位，暂不支持海外手机号
        flowApproverInfo.setMobile(mobile);
        // 身份证
        flowApproverInfo.setIdCardNumber(idCardNum);
        flowApproverInfo.setIdCardType("ID_CARD");
        // 签署审批-false-无需审批
        flowApproverInfo.setApproverNeedSignReview(false);

        // 签署人对应的签署控件
        Component component = BuildKeyWordComponent(keyWord, "Right", offSetX, offsetY, width, height);
        flowApproverInfo.setSignComponents(new Component[]{component});

        return flowApproverInfo;
    }

    //

    /**
     * 打包企业静默签署方参与者信息
     *
     * @param serverSignSealId 平台企业印章ID
     * @param orgName          平台企业名称
     * @return FlowApproverInfo 企业静默签参与人
     */
    private static FlowApproverInfo BuildServerSignApprover(String serverSignSealId, String orgName) {
        // 签署参与者信息
        // 企业签署方
        FlowApproverInfo serverSignApprover = new FlowApproverInfo();
        // 签署人类型
        // PERSON-个人/自然人
        // PERSON_AUTO_SIGN-个人自动签（定制化场景下使用）
        // ORGANIZATION-企业（企业签署方或模板发起时的企业静默签）
        // ENTERPRISESERVER-企业静默签（文件发起时的企业静默签字）
        serverSignApprover.setApproverType("ENTERPRISESERVER");
        // 企业名称
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
        // 上传文件获取fileId
        UploadFilesResponse uploadRes = UploadFiles.uploadFiles(CreateFlowUtils.setAgent(), uploadFiles);
        // fileId
        assert uploadRes != null;
        String fileId = uploadRes.getFileIds()[0];
        return AutoSignCreateFlowByFiles(flowName, approverInfos, fileId);
    }

    /**
     * 通过上传后的pdf资源编号来创建待签署的合同流程
     * 参考文档 https://qian.tencent.com/developers/partnerApis/startFlows/ChannelCreateFlowByFiles
     *
     * @param flowName  流程名
     * @param approvers 签署流程参与者信息
     * @param fileId    pdf文件id
     * @return 流程id即flowId
     */
    public static String AutoSignCreateFlowByFiles(String flowName, FlowApproverInfo[] approvers,
                                                   String fileId) throws TencentCloudSDKException {

        // 构造默认的api客户端调用实例
        EssbasicClient client = CreateFlowUtils.initClient();
        // 实例化一个请求对象,每个接口都会对应一个request对象
        ChannelCreateFlowByFilesRequest req = new ChannelCreateFlowByFilesRequest();
        // 第三方平台应用相关信息
        // 此接口Agent.ProxyOrganizationOpenId、Agent. ProxyOperator.OpenId、Agent.AppId 必填。
        req.setAgent(CreateFlowUtils.setAgent());
        // 签署流程签约方列表，最多不超过5个参与方
        req.setFlowApprovers(approvers);
        // 签署流程名称，长度不超过200个字符
        req.setFlowName(flowName);
        // 签署文件资源Id列表，目前仅支持单个文件
        req.setFileIds(new String[]{fileId});
        // 个人自动签场景。发起自动签署时，需设置对应自动签署场景，目前仅支持场景：处方单-E_PRESCRIPTION_AUTO_SIGN
        req.setAutoSignScene("E_PRESCRIPTION_AUTO_SIGN");
        // true-无序签署
        req.setUnordered(true);
        // 发起方企业的签署人进行签署操作是否需要企业内部审批。
        // 若设置为true,审核结果需通过接口 CreateFlowSignReview 通知电子签，审核通过后，发起方企业签署人方可进行签署操作，否则会阻塞其签署操作。
        // 注：企业可以通过此功能与企业内部的审批流程进行关联，支持手动、静默签署合同。
        req.setNeedSignReview(false);

        ChannelCreateFlowByFilesResponse response = client.ChannelCreateFlowByFiles(req);
        assert response != null;
        return response.getFlowId();
    }


    /**
     * 医疗自动签署-关键字-构建控件信息
     * <p>
     * 详细参考 https://cloud.tencent.com/document/api/1420/61525#Component
     * <p>
     * 在通过文件发起合同时，对应的component有三种定位方式，这里使用关键字坐标方式
     * 绝对定位方式
     * 表单域(FIELD)定位方式
     * 关键字(KEYWORD)定位方式
     * 可以参考官网说明
     * https://cloud.tencent.com/document/product/1323/78346#component-.E4.B8.89.E7.A7.8D.E5.AE.9A.E4.BD.8D.E6.96.B9.E5.BC.8F.E8.AF.B4.E6.98.8E
     * <p>
     * 坐标调整说明
     * https://qian.tencent.com/developers/faq/apis?_highlight=%E5%85%B3%E9%94%AE%E5%AD%97#%E5%85%B3%E9%94%AE%E5%AD%97%E5%AE%9A%E4%BD%8D%E5%A6%82%E4%BD%95%E8%B0%83%E6%95%B4%E6%8E%A7%E4%BB%B6%E4%BD%8D%E7%BD%AE
     *
     * @param componentId      控件ID
     * @param relativeLocation 相对位置
     * @param componentWidth   控件宽度，单位pt
     * @param componentHeight  件高度，单位pt
     * @return 控件
     */
    public static Component BuildKeyWordComponent(String componentId, String relativeLocation,
                                                  float offSetX, float offSetY, float componentWidth,
                                                  float componentHeight) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        // ComponentId 关键字
        component.setComponentId(componentId);

        // 如果是 Component 控件类型，则可选类型为：
        // TEXT - 内容文本控件
        // DATE - 内容日期控件
        // CHECK_BOX - 勾选框控件
        // 如果是 SignComponent 控件类型，则可选类型为：
        // SIGN_SEAL - 签署印章控件
        // SIGN_DATE - 签署日期控件
        // SIGN_SIGNATURE - 手写签名控件
        component.setComponentType("SIGN_SIGNATURE");
        // 参数控件宽度，单位pt
        component.setComponentWidth(componentWidth);
        // 参数控件高度，单位pt
        component.setComponentHeight(componentHeight);
        // 控件所属文件的序号（取值为：0-N）
        component.setFileIndex(0L);
        // 参数控件所在页码，取值为：1-N
        component.setComponentPage(1L);
        // KEYWORD
        component.setGenerateMode("KEYWORD");
        // OffsetX: 20
        component.setOffsetX(offSetX);
        // OffsetY: -30
        component.setOffsetY(offSetY);
        // RelativeLocation: Right
        component.setRelativeLocation(relativeLocation);

        return component;
    }

    /**
     * 构建控件信息
     * 详细参考 https://cloud.tencent.com/document/api/1420/61525#Component
     * <p>
     * 在通过文件发起合同时，对应的component有三种定位方式，这里使用关键字坐标方式
     * 绝对定位方式
     * 表单域(FIELD)定位方式
     * 关键字(KEYWORD)定位方式
     * 可以参考官网说明
     * https://cloud.tencent.com/document/product/1323/78346#component-.E4.B8.89.E7.A7.8D.E5.AE.9A.E4.BD.8D.E6.96.B9.E5.BC.8F.E8.AF.B4.E6.98.8E
     * <p>
     * 坐标调整说明
     * https://qian.tencent.com/developers/faq/apis?_highlight=%E5%85%B3%E9%94%AE%E5%AD%97#%E5%85%B3%E9%94%AE%E5%AD%97%E5%AE%9A%E4%BD%8D%E5%A6%82%E4%BD%95%E8%B0%83%E6%95%B4%E6%8E%A7%E4%BB%B6%E4%BD%8D%E7%BD%AE
     *
     * @param componentType   控件类型，对于签署控件，具体类型如下
     *                        SIGN_SEAL - 签署印章控件
     *                        SIGN_DATE - 签署日期控件
     *                        SIGN_SIGNATURE - 手写签名控件
     * @param componentValue  控件内容
     * @param componentPosX   控件水平方向坐标X位置，单位pt
     * @param componentPosY   控件垂直方向坐标Y位置，单位pt
     * @param componentWidth  控件宽度，单位pt
     * @param componentHeight 件高度，单位pt
     * @param fileIndex       所属文件的序号（取值为：0-N）
     * @param componentPage   控件所在页码，取值为：1-N
     * @return 控件
     */
    public static Component BuildComponent(String componentType, String componentValue, float componentPosX,
                                           float componentPosY, float componentWidth, float componentHeight,
                                           long fileIndex, long componentPage) {
        // 模板控件信息
        // 签署人对应的签署控件
        Component component = new Component();

        // 如果是 Component 控件类型，则可选类型为：
        // TEXT - 内容文本控件
        // DATE - 内容日期控件
        // CHECK_BOX - 勾选框控件
        // 如果是 SignComponent 控件类型，则可选类型为：
        // SIGN_SEAL - 签署印章控件
        // SIGN_DATE - 签署日期控件
        // SIGN_SIGNATURE - 手写签名控件
        component.setComponentType(componentType);
        // 控件Value
        component.setComponentValue(componentValue);
        // 参数控件X位置，单位pt
        component.setComponentPosX(componentPosX);
        // 参数控件Y位置，单位pt
        component.setComponentPosY(componentPosY);
        // 参数控件宽度，单位pt
        component.setComponentWidth(componentWidth);
        // 参数控件高度，单位pt
        component.setComponentHeight(componentHeight);
        // 控件所属文件的序号（取值为：0-N）
        component.setFileIndex(fileIndex);
        // 参数控件所在页码，取值为：1-N
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
