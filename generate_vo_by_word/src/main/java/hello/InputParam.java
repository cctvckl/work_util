package hello;

import lombok.Data;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 10:54
 **/
@Data
public class InputParam {
    String name;

    String desc;

    String paramType;

    Boolean isMandatory;

    String mark;

    List<InputParam> innerClassInputParam;
}
