/*
 * @(#)LogFactory.java 1.0 2017年4月25日
 *
 * Copyright (c) 2013-2017 JiangXi Hangtian PoHuYun(JXHTPHY), Inc.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of JiangXi
 * Hangtian PoHuYun, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with JXHTPHY.
 *
 * JXHTPHY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JXHTPHY SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package com.example.hos.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @类名: LogFactory
 * @描述: 日志工厂类
 * @版本: Version 1.0
 * @创建日期: 2017年4月25日下午2:52:52
 * @作者: Jammy
 * @JDK: 1.8
 */

public class LogFactory {

    private static final Logger ACCESS = LoggerFactory.getLogger("ACCESS");

    private static final Logger OPERATION = LoggerFactory.getLogger("OPERATION");

    private static final Logger BUSINESS = LoggerFactory.getLogger("BUSINESS");

    private static final Logger ERROR = LoggerFactory.getLogger("ERROR");

    private static final Logger DEBUG = LoggerFactory.getLogger("DEBUG");

    private static final Logger MAIL = LoggerFactory.getLogger("MAIL");

    /**
     * @return
     */
    public static Logger getAccessLog() {

        return ACCESS;
    }

    /**
     * @return
     */
    public static Logger getOperationLog() {
        return OPERATION;
    }

    /**
     * @return
     */
    public static Logger getBusinessLog() {
        return BUSINESS;
    }

    /**
     * @return
     */
    public static Logger getErrorLog() {
        return ERROR;
    }

    /**
     * @return
     */
    public static Logger getDebugLog() {

        return DEBUG;
    }

}
