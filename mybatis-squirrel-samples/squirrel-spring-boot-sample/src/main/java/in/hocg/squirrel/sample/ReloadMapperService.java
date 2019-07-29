package in.hocg.squirrel.sample;

import in.hocg.squirrel.spring.boot.autoconfigure.MybatisAutoConfiguration;
import in.hocg.squirrel.spring.boot.autoconfigure.MybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hocgin
 * @date 2019/7/29
 */
@Slf4j
@org.springframework.context.annotation.Configuration
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class ReloadMapperService implements InitializingBean, ApplicationContextAware {
    private Resource[] mapperResources;
    private Configuration configuration;
    private ApplicationContext applicationContext;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("启动监听🐕");
        SqlSessionFactory sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        MybatisProperties mybatisProperties = applicationContext.getBean(MybatisProperties.class);
        mapperResources = mybatisProperties.resolveMapperLocations();
//        sqlSessionFactoryBean = applicationContext.getBean(SqlSessionFactoryBean.class);
        configuration = sqlSessionFactory.getConfiguration();
        new WatchDog().start();
        
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    class WatchDog extends Thread {
        
        @Override
        public void run() {
            
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                for (String path : getWatchPaths()) {
                    Paths.get(path).register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
                }
                while (true) {
                    WatchKey watchKey = watchService.take();
                    Set<String> set = new HashSet<>();
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        set.add(event.context().toString());
                    }
                    reloadXml(set);
                    boolean valid = watchKey.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException | InterruptedException e) {
                log.error("", e);
            }
        }
        
        private void reloadXml(Set<String> set) {
            log.info("需要重新加载的文件列表: {}", set);
            List<Resource> list = Arrays.stream(getResource())
                    .filter(p -> set.contains(p.getFilename()))
                    .collect(Collectors.toList());
            log.info("需要处理的资源路径:{}", list);
            list.forEach(r -> {
                try {
                    clearMap(getNamespace(r));
                    clearSet(r.toString());
                    XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(r.getInputStream(), configuration,
                            r.toString(), configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    log.info("ERROR: 重新加载[{}]失败", r.toString(), e);
                    throw new RuntimeException("ERROR: 重新加载[" + r.toString() + "]失败", e);
                } finally {
                    ErrorContext.instance().reset();
                }
            });
            log.info("成功热部署文件列表: {}", set);
        }
        
        /**
         * 清除文件记录缓存
         *
         * @param resource xml文件路径
         * @date ：2018/12/19
         * @author ：zc.ding@foxmail.com
         */
        private void clearSet(String resource) {
            log.info("清理mybatis的资源{}在容器中的缓存", resource);
            Object value = SystemMetaObject.forObject(configuration).getValue("loadedResources");
            if (value instanceof Set) {
                Set<?> set = (Set) value;
                set.remove(resource);
                set.remove("namespace:" + resource);
            }
        }
        
        private void clearMap(String nameSpace) {
            log.info("清理Mybatis的namespace={}在mappedStatements、caches、resultMaps、parameterMaps、keyGenerators、sqlFragments中的缓存");
            MetaObject metaObject = SystemMetaObject.forObject(configuration);
            Arrays.asList("mappedStatements", "caches", "resultMaps", "parameterMaps", "keyGenerators", "sqlFragments")
                    .forEach(fieldName -> {
                        Object value = metaObject.getValue(fieldName);
                        if (value instanceof Map) {
                            Map<?, ?> map = (Map) value;
                            List<Object> list = map.keySet().stream().filter(o -> o.toString().startsWith(nameSpace + ".")).collect(Collectors.toList());
                            log.info("需要清理的元素: {}", list);
                            list.forEach(k -> map.remove((Object) k));
                        }
                    });
        }
        
        private String getNamespace(Resource resource) {
            log.info("从{}获取namespace", resource.toString());
            try {
                XPathParser parser = new XPathParser(resource.getInputStream(), true, null, new XMLMapperEntityResolver());
                return parser.evalNode("/mapper").getStringAttribute("namespace");
            } catch (Exception e) {
                log.info("ERROR: 解析xml中namespace失败", e);
                throw new RuntimeException("ERROR: 解析xml中namespace失败", e);
            }
        }
        
        private Set<String> getWatchPaths() {
            Set<String> set = new HashSet<>();
            Arrays.stream(getResource()).forEach(r -> {
                try {
                    log.info("资源路径:{}", r.toString());
                    set.add(r.getFile().getParentFile().getAbsolutePath());
                } catch (Exception e) {
                    log.info("获取资源路径失败", e);
                    throw new RuntimeException("获取资源路径失败");
                }
            });
            log.info("需要监听的xml资源: {}", set);
            return set;
        }
        
        private Resource[] getResource() {
            return mapperResources;
        }
        
    }
}
