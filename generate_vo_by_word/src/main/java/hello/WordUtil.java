package hello;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 9:53
 **/
public class WordUtil {
    public static void main(String[] args) {
        testWord("D:\\chromedownload\\电子脚环与接处警系统接口说明V1.0.docx");
    }

    /**
     * 获取word中的pai列表
     * @param filePath
     * @return
     */
    public static ArrayList<APIVO> testWord(String filePath) {
        if (!filePath.toLowerCase().endsWith("docx")) {
            return null;
        }

        //得到word文档的信息
        XWPFDocument xwpf = null;
        try {
            //载入文档 //如果是office2007  docx格式
            FileInputStream in = new FileInputStream(filePath);
            xwpf = new XWPFDocument(in);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (xwpf == null) {
            return null;
        }
        //获取表的列表,排除掉非接口的表
        ArrayList<TableMetaDataVO> tableRows = getTableRows(xwpf);

        ArrayList<APIVO> apivos = convertTableMetaData2ApiVO(tableRows);

        //生成req vo文件
        return apivos;
    }

    private static ArrayList<APIVO> convertTableMetaData2ApiVO(ArrayList<TableMetaDataVO> tableRows) {
        //解析每行，分成几个大的部分。如地址、请求方式、说明、输入参数部分、输出参数部分。
        parseTableRow(tableRows);

        ArrayList<APIVO> apivos = new ArrayList<>();
        for (TableMetaDataVO tableRow : tableRows) {
            APIVO apivo = new APIVO();

            ArrayList<String> apiAddressRow = tableRow.getApiAddressRow();
            if (apiAddressRow != null) {
                apivo.setApiName(apiAddressRow.get(1).replace(".do",""));
            }

            ArrayList<String> apiMethodRow = tableRow.getApiMethodRow();
            if (apiAddressRow != null) {
                apivo.setApiMethod(apiMethodRow.get(1));
            }

            ArrayList<String> apiDescRow = tableRow.getApiDescRow();
            if (apiDescRow != null) {
                apivo.setApiDesc(apiDescRow.get(1));
            }

            //设置输入参数
            List<ArrayList<String>> apiInputParamRows = tableRow.getApiInputParamRows();
            ArrayList<InputParam> inputParams = new ArrayList<>();
            for (ArrayList<String> apiInputParamRow : apiInputParamRows) {
                InputParam param = new InputParam();
                param.setName(apiInputParamRow.get(0));
                param.setDesc(apiInputParamRow.get(1));
                param.setParamType(apiInputParamRow.get(2));
                param.setIsMandatory(Objects.equals("是",apiInputParamRow.get(3)));
                param.setMark(apiInputParamRow.get(4));

                inputParams.add(param);
            }
            apivo.setInputParams(inputParams);

            //设置输出参数
            List<ArrayList<String>> apiOutputParamRows = tableRow.getApiOutputParamRows();
            ArrayList<OutputParam> outputParams = new ArrayList<>();
            for (ArrayList<String> apiOutputParams : apiOutputParamRows) {
                OutputParam param = new OutputParam();
                param.setName(apiOutputParams.get(0));
                param.setDesc(apiOutputParams.get(1));
                param.setParamType(apiOutputParams.get(2));
                param.setIsMandatory(Objects.equals("是",apiOutputParams.get(3)));
                param.setMark(apiOutputParams.get(4));

                if ("code".equals(param.getName())){
                    param.setParamType("String");
                }
                outputParams.add(param);
            }
            apivo.setOutputParams(outputParams);

            apivos.add(apivo);
        }

        return apivos;
    }

    private static void parseTableRow(ArrayList<TableMetaDataVO> tableRows) {
        for (TableMetaDataVO tableRow : tableRows) {

            ArrayList<ArrayList<String>> rows = tableRow.getTableRows();
            int state = EWordParseState.STATUS_INIT.getStatusValue();
            ArrayList<ArrayList<String>> inputParamsRows = new ArrayList<>();
            ArrayList<ArrayList<String>> outputParamsRows = new ArrayList<>();
            for (ArrayList<String> row : rows) {
                if (Objects.equals(state,EWordParseState.STATUS_INIT.getStatusValue())){
                    if (row.contains("接口地址")) {
                        tableRow.setApiAddressRow(row);
                        continue;
                    }

                    if (row.contains("请求方式")) {
                        tableRow.setApiMethodRow(row);
                        continue;
                    }

                    if (row.contains("接口说明")) {
                        tableRow.setApiDescRow(row);
                        continue;
                    }

                    if (row.contains("输入参数")) {
                        state = EWordParseState.STATUS_INPUTPARAM_START.getStatusValue();
                        continue;
                    }


                }else if (Objects.equals(state,EWordParseState.STATUS_INPUTPARAM_START.getStatusValue())){
                    if (row.contains("参数名称")){
                        continue;
                    }

                    //其实这里加不加size判断无所谓，因为进入了该分支，肯定已经是输入参数部分了
                    if (row.size() == 5){
                        inputParamsRows.add(row);
                        continue;
                    }

                    //改变状态
                    if (row.contains("返回信息")){
                        //切换为输入结束，输出开始
                        tableRow.setApiInputParamRows(inputParamsRows);
                        state = EWordParseState.STATUS_INPUTPARAM_END.getStatusValue();
                        state = EWordParseState.STATUS_OUTPUTPARAM_START.getStatusValue();
                        continue;
                    }
                }else if (Objects.equals(state,EWordParseState.STATUS_OUTPUTPARAM_START.getStatusValue())){
                    if (row.contains("参数名称")){
                        continue;
                    }

                    if (row.size() == 5){
                        outputParamsRows.add(row);
                    }
                }

            }//循环结束
            tableRow.setApiOutputParamRows(outputParamsRows);
            state = EWordParseState.STATUS_OUTPUTPARAM_END.getStatusValue();

            System.out.println(tableRow);
        }
    }

    /**
     * 获取表格列表
     * @param xwpf
     * @return
     */
    private static ArrayList<TableMetaDataVO> getTableRows(XWPFDocument xwpf) {
        //得到word中的表格
        Iterator<XWPFTable> it = xwpf.getTablesIterator();
        ArrayList<TableMetaDataVO> tableVOS = new ArrayList<>();
        while (it.hasNext()) {

            XWPFTable table = it.next();

            ArrayList<ArrayList<String>> tableRows = getTableRows(table);

            TableMetaDataVO tableVO = new TableMetaDataVO();
            tableVO.setTableRows(tableRows);

            tableVOS.add(tableVO);
        }

        tableVOS.removeIf(tableVO -> {
            ArrayList<ArrayList<String>> rows = tableVO.getTableRows();
            Boolean isApi = false;
            for (ArrayList<String> row : rows) {
                if (row.contains("接口地址")){
                    isApi = true;
                    break;
                }
            }

            if (!isApi){
                return true;
            }
            return false;
        });

        return tableVOS;
    }

    private static ArrayList<ArrayList<String>> getTableRows(XWPFTable table) {
        ArrayList<ArrayList<String>> rowsString = new ArrayList<>();

        List<XWPFTableRow> rows = table.getRows();
        //读取每一行数据
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow row = rows.get(i);
            //读取每一列数据
            List<XWPFTableCell> cells = row.getTableCells();
            ArrayList<String> cellArray = new ArrayList<>();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                String text = cell.getText();
                cellArray.add(text);
            }

            rowsString.add(cellArray);
        }

        return rowsString;
    }
}
