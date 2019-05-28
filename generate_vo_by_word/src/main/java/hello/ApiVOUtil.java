package hello;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 12:59
 **/
public class ApiVOUtil {
    static String fieldWithComment =
            "    /**\n" +
            "     * %s\n" +
            "     */\n" +
            "    %s %s;\n\n";
    public static void main(String[] args) {
        String abc = String.format(fieldWithComment, "abc","String","name");
        System.out.println(abc);
    }

    public static ArrayList<APIVO> getReqVOFields(APIVO apivo) {
        recorrectInputParamType(apivo);

        ArrayList<APIVO> apivos = new ArrayList<>();
        apivos.add(apivo);

        List<InputParam> inputParams = apivo.getInputParams();
        for (InputParam inputParam : inputParams) {
            //如果参数内层的参数不为空，选择生成一个新的 apivo
            if (!CollectionUtils.isEmpty(inputParam.getInnerClassInputParam())){
                APIVO nestedApivo = new APIVO();
                nestedApivo.setApiName(apivo.getApiName() + inputParam.getName());
                nestedApivo.setApiMethod(apivo.getApiMethod());
                nestedApivo.setApiDesc(apivo.getApiDesc());
                nestedApivo.setInputParams(inputParam.getInnerClassInputParam());

                apivos.add(nestedApivo);
            }
        }

        return apivos;

    }

    public static ArrayList<APIVO> getRespVOFields(APIVO apivo) {
        recorrectOutputParamType(apivo);

        ArrayList<APIVO> apivos = new ArrayList<>();
        apivos.add(apivo);

        List<OutputParam> outputParams = apivo.getOutputParams();
        for (OutputParam outputParam : outputParams) {
            //如果参数内层的参数不为空，选择生成一个新的 apivo
            if (!CollectionUtils.isEmpty(outputParam.getInnerClassOutputParam())){
                APIVO nestedApivo = new APIVO();
                nestedApivo.setApiName(apivo.getApiName() + outputParam.getName());
                nestedApivo.setApiMethod(apivo.getApiMethod());
                nestedApivo.setApiDesc(apivo.getApiDesc());
                nestedApivo.setOutputParams(outputParam.getInnerClassOutputParam());

                apivos.add(nestedApivo);
            }
        }

        return apivos;

    }

    public static String generateInputParamString(APIVO apivo) {
        StringBuilder builder = new StringBuilder();
        for (InputParam inputParam : apivo.getInputParams()) {
            String name = inputParam.getName();
            if (StringUtils.isEmpty(name) || "".equalsIgnoreCase(name.trim())){
                continue;
            }
            String field = String.format(fieldWithComment,inputParam.getDesc(), inputParam.getParamType(), name);

            builder.append(field);
        }

        String s = builder.toString();
        if("".equalsIgnoreCase(s.trim())){
            return null;
        }

        return s;
    }

    private static void recorrectInputParamType(APIVO apivo) {
        //所有的object类型参数
        ArrayList<InputParam> allObjectTypeParam = new ArrayList<>();

        int state = EParamParseState.STATUS_INIT.getStatusValue();
        ArrayList<InputParam> nestedParams = new ArrayList<>();
        InputParam lastObjectTypeParam = null;
        for (InputParam currentInputParam : apivo.getInputParams()) {
            String paramType = currentInputParam.getParamType();
            if ("Array".equalsIgnoreCase(paramType)){
                currentInputParam.setParamType("ArrayList");
                paramType = "ArrayList";
            }

            if (Objects.equals(state,EParamParseState.STATUS_OBJECT_START.getStatusValue())){
                if ("String".equalsIgnoreCase(paramType)){
                    currentInputParam.setParamType("String");
                }

                if ("Int".equalsIgnoreCase(paramType)){
                    currentInputParam.setParamType("Integer");
                }

                //在该状态下遇上object类型，说明上一个object已经结束。收集信息
                if ("ArrayList".equalsIgnoreCase(paramType)){
                    state = EParamParseState.STATUS_OBJECT_END.getStatusValue();
                    lastObjectTypeParam.setInnerClassInputParam(nestedParams);
                    //切换当前currentInputParam
                    lastObjectTypeParam = currentInputParam;
                    continue;
                }

                //用json序列化方式来深拷贝

                nestedParams.add(currentInputParam);
            }else if (Objects.equals(state,EParamParseState.STATUS_INIT.getStatusValue())){
                if ("String".equalsIgnoreCase(paramType)){
                    currentInputParam.setParamType("String");
                    continue;
                }

                if ("Int".equalsIgnoreCase(paramType)){
                    currentInputParam.setParamType("Integer");
                    continue;
                }

                //进入该分支的话，lastObjectTypeParam必定不为空
                if ("ArrayList".equalsIgnoreCase(paramType)){
                    state = EParamParseState.STATUS_OBJECT_START.getStatusValue();
                    lastObjectTypeParam = currentInputParam;

                    allObjectTypeParam.add(lastObjectTypeParam);
                }
            }



        }

        //如果到遍历结束，依然状态为 STATUS_OBJECT_START ，说明整个过程中，只有一个Object类型的参数
        if (Objects.equals(state,EParamParseState.STATUS_OBJECT_START.getStatusValue())){
            lastObjectTypeParam.setInnerClassInputParam(nestedParams);
        }

        // 删除 allObjectTypeParam 之外的param
        if (!CollectionUtils.isEmpty(allObjectTypeParam)){
            Iterator<InputParam> iterator = apivo.getInputParams().iterator();
            while (iterator.hasNext()){
                InputParam next = iterator.next();
                if (allObjectTypeParam.contains(next)){
                    continue;
                }
                iterator.remove();
            }
        }



        System.out.println(apivo);
    }


    private static void recorrectOutputParamType(APIVO apivo) {
        //所有的object类型参数
        ArrayList<OutputParam> allObjectTypeParam = new ArrayList<>();

        int state = EParamParseState.STATUS_INIT.getStatusValue();
        ArrayList<OutputParam> nestedParams = new ArrayList<>();
        OutputParam lastObjectTypeParam = null;
        for (OutputParam currentOutputParam : apivo.getOutputParams()) {
            String paramType = currentOutputParam.getParamType();
            if ("Array".equalsIgnoreCase(paramType)){
                currentOutputParam.setParamType("ArrayList");
                paramType = "ArrayList";
            }

            if (Objects.equals(state,EParamParseState.STATUS_OBJECT_START.getStatusValue())){
                if ("String".equalsIgnoreCase(paramType)){
                    currentOutputParam.setParamType("String");
                }

                if ("Int".equalsIgnoreCase(paramType)){
                    currentOutputParam.setParamType("Integer");
                }

                //在该状态下遇上object类型，说明上一个object已经结束。收集信息
                if ("ArrayList".equalsIgnoreCase(paramType)){
                    state = EParamParseState.STATUS_OBJECT_END.getStatusValue();
                    lastObjectTypeParam.setInnerClassOutputParam(nestedParams);
                    //切换当前currentInputParam
                    lastObjectTypeParam = currentOutputParam;
                    continue;
                }

                //用json序列化方式来深拷贝

                nestedParams.add(currentOutputParam);
            }else if (Objects.equals(state,EParamParseState.STATUS_INIT.getStatusValue())){
                if ("String".equalsIgnoreCase(paramType)){
                    currentOutputParam.setParamType("String");
                    continue;
                }

                if ("Int".equalsIgnoreCase(paramType)){
                    currentOutputParam.setParamType("Integer");
                    continue;
                }

                //进入该分支的话，lastObjectTypeParam必定不为空
                if ("ArrayList".equalsIgnoreCase(paramType)){
                    state = EParamParseState.STATUS_OBJECT_START.getStatusValue();
                    lastObjectTypeParam = currentOutputParam;

                    allObjectTypeParam.add(lastObjectTypeParam);
                }
            }



        }

        //如果到遍历结束，依然状态为 STATUS_OBJECT_START ，说明整个过程中，只有一个Object类型的参数
        if (Objects.equals(state,EParamParseState.STATUS_OBJECT_START.getStatusValue())){
            lastObjectTypeParam.setInnerClassOutputParam(nestedParams);
        }

        // 删除 allObjectTypeParam 之外的param
        if (!CollectionUtils.isEmpty(allObjectTypeParam)){
            Iterator<InputParam> iterator = apivo.getInputParams().iterator();
            while (iterator.hasNext()){
                InputParam next = iterator.next();
                if (allObjectTypeParam.contains(next)){
                    continue;
                }
                iterator.remove();
            }
        }



        System.out.println(apivo);
    }


    public static String generateOutputParamString(APIVO apivo) {
        StringBuilder builder = new StringBuilder();
        for (OutputParam outputParam : apivo.getOutputParams()) {
            String name = outputParam.getName();
            if (StringUtils.isEmpty(name) || "".equalsIgnoreCase(name.trim())){
                continue;
            }
            String field = String.format(fieldWithComment,outputParam.getDesc(), outputParam.getParamType(), name);

            builder.append(field);
        }

        String s = builder.toString();
        if("".equalsIgnoreCase(s.trim())){
            return null;
        }

        return s;
    }
}
