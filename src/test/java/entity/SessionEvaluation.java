package entity;

/**
 * @author shajingzhe
 * @date 2023/4/14 上午10:58
 */
public enum SessionEvaluation {
	/**
	 * 非常满意.
	 */
	VERYSATISFIED("非常满意"), // 非常满意
	/**
	 * 满意.
	 */
	MOSTSATISFIED("满意"), // 满意
	/**
	 * 一般.
	 */
	SATISFIED("一般"), // 一般
	/**
	 * 不满意.
	 */
	NOTSATISFIED("不满意"); // 不满意

	private final String text;

	SessionEvaluation(String text) {
		this.text = text;
	}

	public String getText() {

		return text;

	}
}
