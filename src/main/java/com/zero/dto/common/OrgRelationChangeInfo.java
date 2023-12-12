package com.zero.dto.common;

import com.zero.enums.OrgRelationChangeAction;
import lombok.Data;

import java.util.List;

/**
 * 机构关系改变信息.
 *
 * @Author shajingzhe
 * @Date 2023/10/8 上午10:32
 */
@Data
public class OrgRelationChangeInfo {

	private String selfId;

	private String selfTypeId;

	private List<String> parentIdList;

	private List<String> oldParentIdList;

	private OrgRelationChangeAction action;

}
