package com.github.henryorz.excel4j.type;

import com.github.henryorz.excel4j.util.ResultObject;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public interface DataValidator<T> {
    ResultObject validate(Object obj);
}
