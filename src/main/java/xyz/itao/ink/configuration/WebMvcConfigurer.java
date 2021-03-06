package xyz.itao.ink.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.ValueConstants;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import xyz.itao.ink.annotation.SysLog;
import xyz.itao.ink.filter.EmptyStringParameterFilter;
import xyz.itao.ink.interceptor.BaseInterceptor;
import xyz.itao.ink.interceptor.SysLogInterceptor;

import java.util.List;

/**
 * @author hetao
 * @date 2018-12-08
 * @description
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {
    @Autowired
    BaseInterceptor baseInterceptor;
    @Autowired
    SysLogInterceptor sysLogInterceptor;
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/user/**").addResourceLocations("classpath:/templates/themes/default/static/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/templates/admin/static/");
        registry.addResourceHandler("/templates/themes/**").addResourceLocations("classpath:/templates/themes/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
        registry.addInterceptor(sysLogInterceptor);
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

    /**
     * 处理controller中的argument
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(new AbstractNamedValueMethodArgumentResolver(){

            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {
                return methodParameter.getParameterType().equals(String.class);
            }

            @Override
            protected NamedValueInfo createNamedValueInfo(MethodParameter methodParameter) {
                return  new NamedValueInfo("", false, ValueConstants.DEFAULT_NONE);
            }

            @Override
            protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
                String[] param = request.getParameterValues(name);
                if(param==null){
                    return null;
                }
                if(StringUtils.isEmpty(param[0])){
                    return null;
                }
                return param[0];
            }
        });
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 解决put、delete form传值
     * @return
     */
    @Bean
    public FormContentFilter formContentFilter(){
        return new FormContentFilter();
    }

    /**
     * 将请求参数中的空字符串移出
     * @return
     */
    @Bean
    public EmptyStringParameterFilter emptyStringParameterFilter(){
        return new EmptyStringParameterFilter();
    }
}
