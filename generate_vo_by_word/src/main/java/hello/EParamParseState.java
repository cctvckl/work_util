package hello;

/**
 * desc:
 * @author: caokunliang
 * creat_date: 2019/2/28 0028
 * creat_time: 11:16
 **/
public enum EParamParseState {

	STATUS_INIT(0, "初始状态"),
	STATUS_OBJECT_START(1, "遇上object类型的参数"),
	STATUS_OBJECT_END(2, "结束object类型的参数")
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
	private EParamParseState(int statusValue, String statusName) {
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
