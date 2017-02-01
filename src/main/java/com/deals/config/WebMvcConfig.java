package com.deals.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by gsitgithub on 15/11/2016.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    HandlerInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (authInterceptor != null)
            registry.addInterceptor(authInterceptor);

//        registry.addInterceptor(getYourInterceptor());
//        registry.addInterceptor(yourInjectedInceptor);
        // next two should be avoid -- tightly coupled and not very testable
        /*registry.addInterceptor(new YourInceptor());
        registry.addInterceptor(new HandlerInterceptor() {
            ...
        });*/
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder =  new Jackson2ObjectMapperBuilder();
        ObjectMapper om = builder.build();
        om.setSerializerProvider(new CustomNullStringSerializerProvider());
        converters.add(new MappingJackson2HttpMessageConverter(om));
    }

    // We need to customize the DefaultSerializerProvider so that when it is looking for a NullSerializer it
    // will use one that is class sensitive, writing strings as "" and everything else using the default value.
    static class CustomNullStringSerializerProvider extends DefaultSerializerProvider {

        // A couple of constructors and factory methods to keep the compiler happy
        public CustomNullStringSerializerProvider() { super(); }
        public CustomNullStringSerializerProvider(CustomNullStringSerializerProvider provider, SerializationConfig config,
                                                  SerializerFactory jsf) {
            super(provider, config, jsf);
        }
        @Override
        public CustomNullStringSerializerProvider createInstance(SerializationConfig config,
                                                                 SerializerFactory jsf) {
            return new CustomNullStringSerializerProvider(this, config, jsf);
        }

        // This is the interesting part.  When the property has a null value it will call this method to get the
        // serializer for that null value.  At this point, we have the BeanProperty, which contains information about
        // the field that we are trying to serialize (including the type!)  So we can discriminate on the type to determine
        // which serializer is used to output the null value.
        @Override
        public JsonSerializer<Object> findNullValueSerializer(BeanProperty property) throws JsonMappingException {
            if (property.getType().getRawClass().equals(String.class)) {
                return EmptyStringSerializer.INSTANCE;
            } else {
                return super.findNullValueSerializer(property);
            }
        }
    }

    // This is our fancy serializer that takes care of writing the value desired in the case of a null string.  We could
    // write whatever we want in here, but in order to maintain backward compatibility we choose the empty string
    // instead of something like "joel is awesome."
    static class EmptyStringSerializer extends JsonSerializer<Object> {
        public static final JsonSerializer<Object> INSTANCE = new EmptyStringSerializer();

        private EmptyStringSerializer() {}

        // Since we know we only get to this seralizer in the case where the value is null and the type is String, we can
        // do our handling without any additional logic and write that empty string we are so desperately wanting.
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException, JsonProcessingException {
            jsonGenerator.writeString("");
        }
    }
}
