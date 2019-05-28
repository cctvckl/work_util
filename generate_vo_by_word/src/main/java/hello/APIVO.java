package hello;

import lombok.Data;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 10:51
 **/
@Data
public class APIVO {
    String apiName;


    /**
     * get/post
     */
    String apiMethod;

    String apiDesc;

    List<InputParam> inputParams;

    List<OutputParam> outputParams;


}
