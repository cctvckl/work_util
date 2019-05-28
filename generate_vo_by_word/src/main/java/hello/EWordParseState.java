package hello;

/**
 * desc:
 * @author: caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 11:16
 **/
public enum EWordParseState {

	STATUS_INIT(0, "初始状态"),
	STATUS_INPUTPARAM_START(1, "开始读取输入参数"),
	STATUS_INPUTPARAM_END(2, "输入参数读取完毕"),
    STATUS_OUTPUTPARAM_START(3, "开始读取输出参数"),
    STATUS_OUTPUTPARAM_END(4, "输出参数读取完毕")
	;

	/** 状态值 */
	private int statusValue;

	/** 状态名称 */
	private String statusName;

	/**
	 * <b>构造方法</b>
	 * <br/>
	 *
	 * @param statusValue 状态值
	 * @param statusName 状态名称
	 */
	private EWordParseState(int statusValue, String statusName) {
		this.statusValue = statusValue;
		this.statusName = statusName;
	}

	/**
	 * <b>方法说明：</b>
	 * <ul>
	 * 获取状态值
	 * </ul>
	 * 
	 * @return int 状态值
	 */
	public int getStatusValue() {
		return statusValue;
	}

	/**
	 * <b>方法说明：</b>
	 * <ul>
	 * 获取状态名称
	 * </ul>
	 * 
	 * @return String 状态名称
	 */
	public String getStatusName() {
		return statusName;
	}

}
