/*
 * Copyright 2004-2021 Homolo Co., Ltd. All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 */
package com.zero.utils.thread;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池执行器工厂.
 *
 * @author shijingzhe
 */
public final class ThreadPoolExecutorFactory {

	private ThreadPoolExecutorFactory() {
	}

	public static ThreadPoolExecutor threadPoolExecutorOrgRSInit() {
		return ThreadPoolExecutorHolderOrgRSInit.THREAD_POOL_EXECUTOR;
	}

	public static void executeOrgRSInit(@NonNull Runnable runnable) {
		ThreadPoolExecutorHolderOrgRSInit.THREAD_POOL_EXECUTOR.execute(runnable);
	}

	public static ThreadPoolExecutor threadPoolExecutorCommon() {
		return ThreadPoolExecutorHolderCommon.THREAD_POOL_EXECUTOR;
	}

	public static void executeCommon(@NonNull Runnable runnable) {
		ThreadPoolExecutorHolderCommon.THREAD_POOL_EXECUTOR.execute(runnable);
	}

	public static ThreadPoolExecutor threadPoolExecutorData() {
		return ThreadPoolExecutorHolderData.THREAD_POOL_EXECUTOR;
	}

	public static void executeData(@NonNull Runnable runnable) {
		ThreadPoolExecutorHolderData.THREAD_POOL_EXECUTOR.execute(runnable);
	}

	//上级管理机构初始化专用的线程池;由于需要统计分发任务是否完成.
	private static class ThreadPoolExecutorHolderOrgRSInit {
		//todo bs 待重新配置参数
		static final int CPU = Runtime.getRuntime().availableProcessors();
		static final int CORE_POOL_SIZE = CPU + 1;
		static final int MAXIMUM_POOL_SIZE = CPU * 2 + 1;
		static final long KEEP_ALIVE_TIME = 1L;
		static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
		static final int MAX_QUEUE_NUM = 4000; //todo bs任务池不够，重新配置淘汰方法,重新进入队列

		public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
				CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
				new LinkedBlockingQueue<>(MAX_QUEUE_NUM),
				new NamedThreadFactory("ThreadPoolExecutorFactory-OrgRSInit-", false),
				new ThreadPoolExecutor.AbortPolicy());
	}

	//通用线程池
	private static class ThreadPoolExecutorHolderCommon { //线程池任务数超出问题,超出后不抛异常
		static final int CPU = Runtime.getRuntime().availableProcessors();
		static final int CORE_POOL_SIZE = CPU + 1;
		static final int MAXIMUM_POOL_SIZE = CPU * 2 + 1;
		static final long KEEP_ALIVE_TIME = 1L;
		static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
		static final int MAX_QUEUE_NUM = 100000; //todo bs任务池不够，重新配置淘汰方法,重新进入队列

		public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
				CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
				new LinkedBlockingQueue<>(MAX_QUEUE_NUM),
				new NamedThreadFactory("ThreadPoolExecutorFactory-Common-", false),
				new ThreadPoolExecutor.AbortPolicy());
	}

	//数据库操作线程池
	private static class ThreadPoolExecutorHolderData { //线程池任务数超出问题,超出后不抛异常
		static final int CPU = Runtime.getRuntime().availableProcessors();
		static final int CORE_POOL_SIZE = CPU + 1;
		static final int MAXIMUM_POOL_SIZE = CPU * 2 + 1;
		static final long KEEP_ALIVE_TIME = 1L;
		static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
		static final int MAX_QUEUE_NUM = 100000; //todo bs任务池不够，重新配置淘汰方法,重新进入队列

		public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
				CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT,
				new LinkedBlockingQueue<>(MAX_QUEUE_NUM),
				new NamedThreadFactory("ThreadPoolExecutorFactory-Data-", false),
				new ThreadPoolExecutor.AbortPolicy());
	}

}
