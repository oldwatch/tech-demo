package org.demo.wechat.aop;

import org.demo.wechat.TokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class BeanFactoryPostProcessForService implements BeanFactoryPostProcessor, EnvironmentAware, ResourceLoaderAware {


    private final static Logger log = LoggerFactory.getLogger(BeanFactoryPostProcessForService.class);

    private final TokenStore store;
    private final RestTemplate template;
    private Environment environment;
    private ResourceLoader resourceLoader;

    public BeanFactoryPostProcessForService(TokenStore store, RestTemplate template) {
        this.store = store;
        this.template = template;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        var scanner = new ClassPathScanningCandidateComponentProviderForApiService(environment, resourceLoader);

        scanner.findCandidateComponents("org.demo.wechat").stream()
                .map(define -> (ScannedGenericBeanDefinition) define)
                .forEach(define -> {

                    var clsName = define.getBeanClassName();
                    try {
                        var cls = Class.forName(define.getBeanClassName());
                        var factory = new WeChatApiFactoryBean(store, template, cls);
                        beanFactory.registerSingleton("api_service_" + clsName, factory);

                    } catch (ClassNotFoundException e) {
                        log.warn("instance fail ", e);
                    }

                });
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private static class ClassPathScanningCandidateComponentProviderForApiService extends ClassPathScanningCandidateComponentProvider {

        ClassPathScanningCandidateComponentProviderForApiService(Environment environment, ResourceLoader resourceLoader) {
            super(false, environment);
            addIncludeFilter(new InterfaceTypeFilter(ApiService.class));
            setResourceLoader(resourceLoader);

        }

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            AnnotationMetadata metadata = beanDefinition.getMetadata();
            return (metadata.isIndependent() && metadata.isInterface());
        }

    }

    private static class InterfaceTypeFilter extends AssignableTypeFilter {


        private InterfaceTypeFilter(Class<?> targetType) {
            super(targetType);
        }

        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            if (metadataReader.getClassMetadata().getClassName().equals(getTargetType().getName())) {
                return false;
            }
            return metadataReader.getClassMetadata().isInterface() && super.match(metadataReader, metadataReaderFactory);
        }
    }

}
