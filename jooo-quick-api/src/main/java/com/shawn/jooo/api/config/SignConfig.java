package com.shawn.jooo.api.config;

import com.shawn.jooo.framework.exception.ApiException;
import com.shawn.jooo.framework.constant.ErrorCode;
import com.shawn.jooo.framework.exception.ApiParamException;
import com.shawn.jooo.framework.utils.SignUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
public class SignConfig extends WebMvcConfigurationSupport {

    @Value("${app.sign.secret}")
    private String secret;

    @Value("${app.sign.check}")
    private boolean checkSign;

    @Value("${app.sign.timestampKey:timestamp}")
    private String TIMESTAMP_KEY;

    @Value("${app.sign.signKey:sign}")
    private String SIGN_KEY;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInterceptor())
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 签名验证器
     */
    private class SignInterceptor implements AsyncHandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if (!checkSign) {
                return true;
            }

            String timestamp = request.getParameter(TIMESTAMP_KEY);
            if (StringUtils.isBlank(timestamp)) {
                throw new ApiParamException(TIMESTAMP_KEY, ErrorCode.ERROR_BAD_PARAM.getCode(), ErrorCode.ERROR_BAD_PARAM.getMessage());
            }

            long time = NumberUtils.toLong(timestamp);
            //前端的时间戳与服务器当前时间戳相差如果大于5分钟，判定当前请求的timestamp无效
            if (Math.abs(time - System.currentTimeMillis() / 1000) > 300) {
                throw new ApiException(ErrorCode.ERROR_BAD_TIME.getCode(), ErrorCode.ERROR_BAD_TIME.getMessage());
            }

            String sign = request.getParameter(SIGN_KEY);
            if (StringUtils.isBlank(sign)) {
                throw new ApiParamException(SIGN_KEY, ErrorCode.ERROR_BAD_PARAM.getCode(), ErrorCode.ERROR_BAD_PARAM.getMessage());
            }

            if (!SignUtils.checkSign(request, secret)) {
                throw new ApiException(ErrorCode.ERROR_BAD_SIGN.getCode(), ErrorCode.ERROR_BAD_SIGN.getMessage());
            }
            return true;
        }
    }
}

