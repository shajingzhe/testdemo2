package com.zero.dto.common;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.Data;

/**
 * entity信息.
 *
 * @Author shajingzhe
 * @Date 2023/9/23 下午4:40
 */
@Data
public class EntityInfo {

	private String entityId;
	private String entityTypeId;

	public EntityInfo(String entityId, String entityTypeId) {
		this.entityId = entityId;
		this.entityTypeId = entityTypeId;
	}


	/**
	 * 所有临近父节点数据.
	 */
	private ConcurrentHashSet<String> parentEntityIdSet = new ConcurrentHashSet<>();

	/**
	 * 所有临近的子节点数据.
	 */
	private ConcurrentHashSet<String> childEntityIdSet = new ConcurrentHashSet<>();

	/**
	 * 所有父节点数据（上级管理机构）[包含自身节点].
	 */
	private ConcurrentHashSet<String> allParentEntityIdSet = new ConcurrentHashSet<>();

}
