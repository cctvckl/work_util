package hello;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 最常见的问题：
 * java.io.FileNotFoundException: xxx does not exist. 解决方法：要有耐心
 * FreeMarker jar 最新的版本（2.3.23）提示 Configuration 方法被弃用
 * 代码自动生产基本原理：
 * 数据填充 freeMarker 占位符
 */
public class VOGenerateDemo {

    private static final String TEMPLATE_PATH = "F:\\ownprojects\\gs-rest-service\\complete\\src\\main\\java\\hello\\templates";
    private static final String CLASS_PATH = "F:\\ownprojects\\gs-rest-service\\complete\\src\\main\\java\\com\\ceiec\\vo\\req";
    private static final String RESP_CLASS_PATH = "F:\\ownprojects\\gs-rest-service\\complete\\src\\main\\java\\com\\ceiec\\vo\\resp";

    public static void main(String[] args) {
        ArrayList<APIVO> apivos = WordUtil.testWord("D:\\chromedownload\\电子脚环与接处警系统接口说明V1.0.docx");


        for (APIVO apivo : apivos) {
//            generateReqVO(apivo);
            generateRespVO(apivo);
        }


    }

    private static void generateReqVO(APIVO apivo) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));

            // step4 加载模版文件
            Template template = configuration.getTemplate("ElectronicFootRingAlarmReqVO.ftl");

            ArrayList<APIVO> paresedApivos = ApiVOUtil.getReqVOFields(apivo);
            for (APIVO item : paresedApivos) {
                String fields = ApiVOUtil.generateInputParamString(item);
                if (fields == null) {
                    continue;
                }

                // step3 创建数据模型
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("classPath", "com.ceiec.vo.req");
                String className = item.getApiName() + "ReqVO";
                dataMap.put("className", className);
                dataMap.put("fields", fields);
                dataMap.put("desc",item.getApiDesc());

                // step5 生成数据
                File docFile = new File(CLASS_PATH + "\\" + className + ".java");
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));

                // step6 输出文件
                template.process(dataMap, out);
                System.out.println(className + ".java 文件创建成功 !");
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    private static void generateRespVO(APIVO apivo) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));

            // step4 加载模版文件
            Template template = configuration.getTemplate("ElectronicFootRingAlarmRespVO.ftl");

            ArrayList<APIVO> paresedApivos = ApiVOUtil.getRespVOFields(apivo);
            for (APIVO item : paresedApivos) {
                String fields = ApiVOUtil.generateOutputParamString(item);
                if (fields == null) {
                    continue;
                }

                // step3 创建数据模型
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("classPath", "com.ceiec.vo.resp");
                String className = item.getApiName() + "RespVO";
                dataMap.put("className", className);
                dataMap.put("fields", fields);
                dataMap.put("desc",item.getApiDesc());

                // step5 生成数据
                File docFile = new File(RESP_CLASS_PATH + "\\" + className + ".java");
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));

                // step6 输出文件
                template.process(dataMap, out);
                System.out.println(className + ".java 文件创建成功 !");
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
