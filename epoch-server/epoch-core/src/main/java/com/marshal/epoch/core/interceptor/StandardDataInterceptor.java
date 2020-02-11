package com.marshal.epoch.core.interceptor;

import com.marshal.epoch.core.dto.AuthenticationUser;
import com.marshal.epoch.core.dto.BaseDto;
import com.marshal.epoch.core.util.ReflectUtil;
import com.marshal.epoch.core.util.RequestHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

import static org.apache.ibatis.mapping.SqlCommandType.INSERT;

/**
 * @auth: Marshal
 * @date: 2019/7/12
 * @desc: 数据新增/更新拦截器,记录数据的更新、新增时间、修改/新增人
 */
@Component
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class StandardDataInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof Executor) {

            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object param = invocation.getArgs()[1];

            BoundSql boundSql = mappedStatement.getBoundSql(param);

            //当前认证用户信息
            AuthenticationUser user = RequestHelper.getCurrentUser();
            Long userId = user.getUserId();

            ReflectUtil.setFieldValue(param, BaseDto.FILED_LAST_UPDATED_BY, userId);
            ReflectUtil.setFieldValue(param, BaseDto.FILED_LAST_UPDATE_DATE, new Date());

            if (INSERT.equals(mappedStatement.getSqlCommandType())) {
                ReflectUtil.setFieldValue(param, BaseDto.FILED_CREATED_BY, userId);
                ReflectUtil.setFieldValue(param, BaseDto.FILED_CREATION_DATE, new Date());
            }

            ReflectUtil.setFieldValue(boundSql, "parameterObject", param);

        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}