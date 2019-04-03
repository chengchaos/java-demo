package com.example.myjsp.service;

import com.example.myjsp.bean.MyName;
import com.example.myjsp.bean.Name;
import com.example.myjsp.bean.Result;

import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/29 0029 下午 4:14 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public interface SomeThingService {

    Result<Map<String, Object>> someRest(MyName myName);
}
