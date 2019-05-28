package hello;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 10:57
 **/
@Data
public class TableMetaDataVO {
    /**
     * 表中的每行
     */
    ArrayList<ArrayList<String>> tableRows;

    ArrayList<String> apiAddressRow;

    ArrayList<String> apiMethodRow;

    ArrayList<String> apiDescRow;

    List<ArrayList<String>> apiInputParamRows;

    List<ArrayList<String>> apiOutputParamRows;

}
