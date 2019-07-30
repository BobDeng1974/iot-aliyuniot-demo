package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 *
 * @Author:   ZhaoLei
 * @Version   1.0.0
 *
 * @Description: 通用父级接口MyMapper,该接口不能被扫描到，否则会报错
 * @Date:     2018/10/15 1:23
 * Copyright (C), 2016-2018, 慧创科技有限公司
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
