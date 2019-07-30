package com.thunk.iot.aliyun.iot.config;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *
 * @Author:   zhaolei
 * @Version   1.0.0
 *
 * @Description: 实例化阿里云物联网平台连接
 * @Date:     2019/5/8 11:05
 * Copyright (C), 2016-2018, EasyLinker V3
 */

@Configuration
@Component
public class AliyunIotConfiguration {

    /**
     * 装载开放API配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "aliyun.iot")
    public AliyunIotConfig aliyunConfig(){
        return new AliyunIotConfig();
    }


    /**
     * 装载H2C配置（服务端订阅）
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "aliyun.h2c")
    public AliyunIotH2cConfig aliyunIotH2cConfig(){
        return new AliyunIotH2cConfig();
    }


    /**
     * 初始化开放API SDK
     * @param aliyunConfig
     * @return
     * @throws ClientException
     */
    @Bean
    public DefaultAcsClient acsClient(AliyunIotConfig aliyunConfig) throws ClientException {
        DefaultProfile.addEndpoint(aliyunConfig.getEndpointName(), aliyunConfig.getRegionId(), aliyunConfig.getProduct(), aliyunConfig.getDomain());
        IClientProfile profile = DefaultProfile.getProfile(aliyunConfig.getRegionId(), aliyunConfig.getAccessKey(), aliyunConfig.getAccessSecret());
        return new DefaultAcsClient(profile);
    }

    /**
     * 初始化服务端订阅，建立连接
     * @param h2cConfig
     * @return
     */
    @Bean
    public MessageClient messageClient(AliyunIotH2cConfig h2cConfig){
        Profile profile = Profile.getAccessKeyProfile(h2cConfig.getEndPoint(), h2cConfig.getRegionId(), h2cConfig.getAccessKey(), h2cConfig.getAccessSecret());
        return MessageClientFactory.messageClient(profile);
    }
}
