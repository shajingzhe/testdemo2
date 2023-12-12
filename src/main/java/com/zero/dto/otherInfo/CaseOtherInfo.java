package com.zero.dto.otherInfo;

import lombok.Data;

/**
 * 案件对接额外信息.
 *
 * @Author shajingzhe
 * @Date 2023/10/20 上午11:08
 */
@Data
public class CaseOtherInfo extends NonLitOtherInfoCommon {

	/**
	 * 非诉案件id
	 */
	private String nonLitCaseId;
}
