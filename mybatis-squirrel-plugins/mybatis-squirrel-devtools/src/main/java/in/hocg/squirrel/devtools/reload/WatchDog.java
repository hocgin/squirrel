package in.hocg.squirrel.devtools.reload;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by hocgin on 2019-07-30.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class WatchDog implements Runnable {
    private final SqlSessionFactory sqlSessionFactory;
    private final Resource[] mapperLocations;
    private final boolean enabled;
    private Long beforeTime = 0L;
    private Set<String> fileSet;
    private Configuration configuration;
    private int delaySeconds = 1;
    private int sleepSeconds = 1;
    
    public WatchDog(Resource[] mapperLocations,
                    SqlSessionFactory sqlSessionFactory,
                    boolean enabled) {
        this.mapperLocations = mapperLocations.clone();
        this.sqlSessionFactory = sqlSessionFactory;
        this.enabled = enabled;
        this.configuration = sqlSessionFactory.getConfiguration();
        this.run();
    }
    
    @Override
    public void run() {
        if (enabled) {
            try {
                Thread.sleep(delaySeconds * 1000);
                for (String filePath : getWatchPaths()) {
                    File file = new File(filePath);
                    if (file.isFile() && file.lastModified() > beforeTime) {
                        refresh(new FileSystemResource(file));
                    }
                    beforeTime = System.currentTimeMillis();
                }
                Thread.sleep(sleepSeconds * 1000);
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException | InterruptedException e) {
                log.error("监控文件刷新出错:", e);
            } finally {
                this.run();
            }
        }
    }
    
    /**
     * 刷新 Mapper 文件
     *
     * @param resource
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void refresh(Resource resource) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        this.configuration = sqlSessionFactory.getConfiguration();
        boolean isSupper = configuration.getClass().getSuperclass() == Configuration.class;
        try {
            Field loadedResourcesField = isSupper ? configuration.getClass().getSuperclass().getDeclaredField("loadedResources")
                    : configuration.getClass().getDeclaredField("loadedResources");
            loadedResourcesField.setAccessible(true);
            Set loadedResourcesSet = ((Set) loadedResourcesField.get(configuration));
            XPathParser xPathParser = new XPathParser(resource.getInputStream(), true, configuration.getVariables(),
                    new XMLMapperEntityResolver());
            XNode context = xPathParser.evalNode("/mapper");
            String namespace = context.getStringAttribute("namespace");
            Field field = MapperRegistry.class.getDeclaredField("knownMappers");
            field.setAccessible(true);
            Map mapConfig = (Map) field.get(configuration.getMapperRegistry());
            mapConfig.remove(Resources.classForName(namespace));
            loadedResourcesSet.remove(resource.toString());
            configuration.getCacheNames().remove(namespace);
            cleanParameterMap(context.evalNodes("/mapper/parameterMap"), namespace);
            cleanMappedStatement(context.evalNodes("insert|update|select|delete"), namespace);
            cleanResultMap(context.evalNodes("/mapper/resultMap"), namespace);
            cleanKeyGenerators(context.evalNodes("insert|update"), namespace);
            cleanSqlElement(context.evalNodes("/mapper/sql"), namespace);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(resource.getInputStream(),
                    sqlSessionFactory.getConfiguration(),
                    resource.toString(), sqlSessionFactory.getConfiguration().getSqlFragments());
            xmlMapperBuilder.parse();
            log.debug("刷新 Mapper 文件: {}", resource);
        } catch (IOException e) {
            log.error("刷新 Mapper 文件错误:", e);
        } finally {
            ErrorContext.instance().reset();
        }
    }
    
    
    /**
     * 清除参数
     *
     * @param list
     * @param namespace
     */
    private void cleanParameterMap(List<XNode> list, String namespace) {
        for (XNode parameterMapNode : list) {
            String id = parameterMapNode.getStringAttribute("id");
            configuration.getParameterMaps().remove(namespace + "." + id);
        }
    }
    
    /**
     * 清除 MappedStatement
     *
     * @param list
     * @param namespace
     */
    private void cleanMappedStatement(List<XNode> list, String namespace) {
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        Map<String, MappedStatement> mappedStatementMap = Maps.newHashMap();
        for (MappedStatement mappedStatement : mappedStatements) {
            mappedStatementMap.put(mappedStatement.getId(), mappedStatement);
        }
        for (XNode node : list) {
            String id = node.getStringAttribute("id");
            mappedStatementMap.remove(namespace + "." + id);
        }
        DefaultSqlSession.StrictMap<Object> objectStrictMap = new DefaultSqlSession.StrictMap<>();
        objectStrictMap.putAll(mappedStatementMap);
        SystemMetaObject.forObject(configuration).setValue("mappedStatements", objectStrictMap);
    }
    
    /**
     * 清除 resultMap 节点
     *
     * @param list
     * @param namespace
     */
    private void cleanResultMap(List<XNode> list, String namespace) {
        for (XNode resultMapNode : list) {
            String id = resultMapNode.getStringAttribute("id", resultMapNode.getValueBasedIdentifier());
            configuration.getResultMapNames().remove(id);
            configuration.getResultMapNames().remove(namespace + "." + id);
            clearResultMap(resultMapNode, namespace);
        }
    }
    
    /**
     * 清除 resultMap 嵌套节点
     *
     * @param xNode
     * @param namespace
     */
    private void clearResultMap(XNode xNode, String namespace) {
        for (XNode resultChild : xNode.getChildren()) {
            if ("association".equals(resultChild.getName()) || "collection".equals(resultChild.getName())
                    || "case".equals(resultChild.getName())) {
                if (resultChild.getStringAttribute("select") == null) {
                    configuration.getResultMapNames().remove(
                            resultChild.getStringAttribute("id", resultChild.getValueBasedIdentifier()));
                    configuration.getResultMapNames().remove(
                            namespace + "." + resultChild.getStringAttribute("id", resultChild.getValueBasedIdentifier()));
                    if (resultChild.getChildren() != null && !resultChild.getChildren().isEmpty()) {
                        clearResultMap(resultChild, namespace);
                    }
                }
            }
        }
    }
    
    /**
     * 清除 selectKey 节点
     *
     * @param list
     * @param namespace
     */
    private void cleanKeyGenerators(List<XNode> list, String namespace) {
        for (XNode context : list) {
            String id = context.getStringAttribute("id");
            configuration.getKeyGeneratorNames().remove(id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
            configuration.getKeyGeneratorNames().remove(namespace + "." + id + SelectKeyGenerator.SELECT_KEY_SUFFIX);
        }
    }
    
    /**
     * 清除 SQL 节点
     *
     * @param list
     * @param namespace
     */
    private void cleanSqlElement(List<XNode> list, String namespace) {
        for (XNode context : list) {
            String id = context.getStringAttribute("id");
            configuration.getSqlFragments().remove(id);
            configuration.getSqlFragments().remove(namespace + "." + id);
        }
    }
    
    /**
     * 获取要监控的文件
     *
     * @return
     */
    private Set<String> getWatchPaths() {
        if (fileSet == null) {
            fileSet = new HashSet<>();
            if (mapperLocations != null) {
                for (Resource mapperLocation : mapperLocations) {
                    try {
                        fileSet.add(mapperLocation.getFile().getPath());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
        return fileSet;
    }
}
