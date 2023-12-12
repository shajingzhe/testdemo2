package com.zero.dto.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 机构信息.
 * @Author shajingzhe
 * @Date 2023/9/23 下午4:48
 */
@Data
public class Relationship implements Serializable {

	private EntityInfo entityInfo;
	private List<EntityInfo> childEntityInfoList;

	private void test(){
		System.out.println("执行");
	}
}
