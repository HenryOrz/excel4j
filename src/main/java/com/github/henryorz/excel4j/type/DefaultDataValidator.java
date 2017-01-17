package com.github.henryorz.excel4j.type;

import com.github.henryorz.excel4j.util.ResultObject;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class DefaultDataValidator<T> implements DataValidator {
    public ResultObject validate(Object obj) {
        return new ResultObject(true, "", obj);
    }
}
