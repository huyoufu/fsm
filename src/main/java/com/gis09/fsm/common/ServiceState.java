package com.gis09.fsm.common;
/**
 * @author xiaohu
 * 2016年4月26日下午10:07:25
 * @description service state enum
 */
public enum ServiceState {
    /**
     * Service just created,not start
     */
    CREATE_JUST,
    /**
     * Service Running
     */
    RUNNING,
    /**
     * Service shutdown
     */
    SHUTDOWN_ALREADY,
    /**
     * Service Start failure
     */
    START_FAILED;
}