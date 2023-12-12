package com.zero.entity;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	private EntityInfo parentEntityInfo;

	private List<EntityInfo> childEntityInfoList = Collections.synchronizedList(new ArrayList<>());

	private ConcurrentHashSet<String> parentEntityIdSet = new ConcurrentHashSet<>();

	/**
	 * 记录该数上所有几点的id，根节点特有.
	 */
	private ConcurrentHashSet<String> childEntityIdSet = new ConcurrentHashSet<>();

}
