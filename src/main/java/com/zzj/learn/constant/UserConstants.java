package com.zzj.learn.constant;

import com.zzj.learn.utils.TokenUtil;

public class UserConstants {
    public static final String REDIS_USER = "smile";
    public static final long REDIS_USER_TIME = TokenUtil.ttlMillis;
    public static final String CURRENT_USER = "current_user";
}
