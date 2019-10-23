package com.ceiec.util;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: Product
 * @author: Mr.Fyf
 * @create: 2018-05-30 16:30
 **/
public class CommentUtil {
    /**
     * 类名正则
     */
    static Pattern classNamePattern = Pattern.compile(".*\\s+class\\s+(\\w+)\\s+.*\\{");

    /**
     * 单行注释
     */
    static Pattern singleLineCommentPattern = Pattern.compile("/\\*\\*\\s+(.*)\\*/");

    /**
     * field
     */
    static Pattern fieldPattern = Pattern.compile("private\\s+(\\w+)\\s+(.*);");


    private static final int MULTI_COMMENT_NOT_START = 0;

    private static final int MULTI_COMMENT_START = 1;

    private static final int MULTI_COMMENT_END = 2;

    public static void main(String[] args) throws IOException {
        HashMap<String, HashMap<String, String>> commentMap = constructTableCommentMap();
        System.out.println(JSON.toJSONString(commentMap));
    }

    public static HashMap<String, HashMap<String, String>> constructTableCommentMap() {
        HashMap<String, HashMap<String, String>> tableFieldCommentsMap = new HashMap<>();
        File dir = new File("F:\\workproject_codes\\bol_2.0_from_product_version\\CAD_Model\\src\\main\\java\\com\\ceiec\\model");
        File[] files = dir.listFiles();
        try {
//
            for (File fileItem : files) {
                processSingleFile(fileItem,tableFieldCommentsMap);
            }
//            File fileItem = new File("F:\\workproject_codes\\bol_2.0_from_product_version\\SYS_Model\\src\\main\\java\\com\\ceiec\\scm\\model\\ConsultingParentType.java");
//            processSingleFile(fileItem, tableFieldCommentsMap);
        } catch (Exception e) {

        }

        return tableFieldCommentsMap;

    }


    public static void processSingleFile(File fileItem, HashMap<String, HashMap<String, String>> tableFieldCommentsMap) throws IOException {
        FileReader reader = null;
        try {
            reader = new FileReader(fileItem);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = null;

        ArrayList<String> multiLineComments = new ArrayList<>();
        int multiLineCommentsState = MULTI_COMMENT_NOT_START;

        boolean classStarted = false;

        ArrayList<FieldCommentVO> list = new ArrayList<>();
        String className = null;
        String lastSingleLineComment = null;
        while ((line = bufferedReader.readLine()) != null) {
            Matcher matcher = classNamePattern.matcher(line);
            boolean b = matcher.find();
            if (b) {
                className = matcher.group(1);
                classStarted = true;
                continue;
            }

            if (!classStarted) {
                continue;
            }

            if (line.contains("serialVersionUID")) {
                continue;
            }

            if (multiLineCommentsState == MULTI_COMMENT_NOT_START) {
                if (line.trim().equals("/**")) {
                    multiLineCommentsState = MULTI_COMMENT_START;
                    continue;
                }
            }

            if (multiLineCommentsState == MULTI_COMMENT_START) {
                multiLineComments.add(line);
                if (line.trim().equals("*/") || line.trim().contains("*/")) {

                    for (String multiLineComment : multiLineComments) {
                        if (multiLineComment.trim().equals("/**") || multiLineComment.trim().equals("*/")) {
                            continue;
                        }
                        if (lastSingleLineComment == null) {
                            lastSingleLineComment = multiLineComment;
                        } else {
                            lastSingleLineComment = multiLineComment + lastSingleLineComment;
                        }
                    }
                    lastSingleLineComment = lastSingleLineComment.replaceAll("/", "").replaceAll("\\*", "").replaceAll("\\t", "");
                    multiLineComments.clear();
                    multiLineCommentsState = MULTI_COMMENT_NOT_START;
                    continue;
                }
                continue;
            }




            Matcher singleLineMathcer = singleLineCommentPattern.matcher(line);
            boolean b1 = singleLineMathcer.find();
            if (b1) {
                lastSingleLineComment = singleLineMathcer.group(1);
                continue;
            }


            Matcher filedMatcher = fieldPattern.matcher(line);
            boolean b2 = filedMatcher.find();
            if (b2) {
                String fieldName = filedMatcher.group(2);

                if (lastSingleLineComment != null) {
                    FieldCommentVO vo = new FieldCommentVO(fieldName, lastSingleLineComment);
                    list.add(vo);

                    lastSingleLineComment = null;
                }
            }

        }

        if (list.size() == 0) {
            return;
        }
        HashMap<String, String> fieldCommentMap = new HashMap<>();
        for (FieldCommentVO fieldCommentVO : list) {
            fieldCommentMap.put(fieldCommentVO.getFieldName().toLowerCase(), fieldCommentVO.getComment().trim());
        }

        tableFieldCommentsMap.put(className.toUpperCase(), fieldCommentMap);
    }
}
