package com.kingbbode.bot.common.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by YG 2016-03-28
 */

@Configuration
@PropertySource(
		ignoreResourceNotFound = true,
		value = {
				"classpath:/properties/version.properties",
				"classpath:/properties/bot.properties",
				"file:/data/etc/bot/bot.properties"
		}
)
public class EnvironmentConfig {
	
}
