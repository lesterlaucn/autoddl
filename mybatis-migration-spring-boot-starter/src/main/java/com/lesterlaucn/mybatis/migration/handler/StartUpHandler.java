package com.lesterlaucn.mybatis.migration.handler;


/**
 * 启动时执行处理的接口
 * @author chenbin.sun
 *
 */
public interface StartUpHandler {

	/**
	 * 建表开始
	 */
	void startHandler();
}
