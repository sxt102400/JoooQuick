package com.shawn.jooo.admin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "spring",value = "quartz")
public class QuartzConfig {
}
