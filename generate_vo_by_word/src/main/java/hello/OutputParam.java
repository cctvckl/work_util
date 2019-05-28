package hello;

import lombok.Data;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 10:55
 **/
@Data
public class OutputParam {
    String name;

    String desc;

    String paramType;

    Boolean isMandatory;

    String mark;

    List<OutputParam> innerClassOutputParam;
}
