package com.kingbbode.bot.common.base.factory;

import com.kingbbode.bot.common.annotations.Brain;
import com.kingbbode.bot.common.annotations.BrainCell;
import com.kingbbode.bot.common.base.cell.*;
import com.kingbbode.bot.common.base.emoticon.Emoticon;
import com.kingbbode.bot.common.base.knowledge.component.KnowledgeComponent;
import com.kingbbode.bot.common.base.setting.BotProperties;
import com.kingbbode.bot.common.enums.BrainRequestType;
import org.apache.commons.lang3.text.WordUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by YG on 2017-01-23.
 */
@Component
public class BrainFactory {

    private static final Logger logger = LoggerFactory.getLogger(BrainFactory.class);

    @Autowired
    private BotProperties botProperties;
    
    @Autowired
    private BeanFactory beanFactory;
    
    @Autowired
    private Emoticon emoticon;
    
    @Autowired
    private KnowledgeComponent knowledge;

    private KnowledgeBrainCell knowledgeBrainCell;

    private Map<String, AbstractBrainCell> brainCellMap;

    @PostConstruct
    public void init() throws InvocationTargetException, IllegalAccessException, IOException {
        knowledgeBrainCell = new KnowledgeBrainCell(knowledge);
        this.load(false);
    }
    
    public void update() throws IOException, InvocationTargetException, IllegalAccessException {
        this.load(true);
    }

    public void load(boolean isUpdate) throws InvocationTargetException, IllegalAccessException, IOException {
        if(isUpdate){
            knowledge.init();
            emoticon.init();
        }
        Map<String, AbstractBrainCell> map = new HashMap<>();
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.zum")).setScanners(
                        new TypeAnnotationsScanner(), new SubTypesScanner()));
        for(Class<?> clazz : reflections.getTypesAnnotatedWith(Brain.class)){
            if(clazz.getAnnotation(Brain.class).request() == BrainRequestType.COMMON) {
                for (Method method : clazz.getMethods()) {
                    if (method.isAnnotationPresent(BrainCell.class)) {
                        BrainCell brainCell = method.getAnnotation(BrainCell.class);
                        map.put(botProperties.getPrefix() + brainCell.key(), new CommonBrainCell(brainCell, clazz, method, beanFactory));
                    }
                }
            }else if(clazz.getAnnotation(Brain.class).request() == BrainRequestType.API){
                Method execute = null;
                Method getCommands = null;
                Method update = null;
                for(Method method : clazz.getMethods()){
                    if(method.getName().equals("execute")){
                        execute = method;
                    }else if(method.getName().equals("getCommands")){
                        getCommands = method;
                    }else if(method.getName().equals("update")){
                        update = method;
                    }
                }
                if(execute == null || getCommands == null){
                    continue;
                }
                if(isUpdate && update != null){
                    update.invoke(beanFactory.getBean(WordUtils.uncapitalize(clazz.getSimpleName())));
                }
                Set<String> list = (Set<String>) getCommands.invoke(beanFactory.getBean(WordUtils.uncapitalize(clazz.getSimpleName())));
                for(String command : list){
                    map.put(command, new ApiBrainCell(clazz, execute, beanFactory));
                }
            }
        }
        for(Map.Entry<String, String> entry : emoticon.getEmoticons().entrySet()){
            map.put(entry.getKey(), new EmoticonBrainCell(entry.getValue()));
        }
        this.brainCellMap = map;
    }

    public boolean contains(String command) {
        return brainCellMap.containsKey(command);
    }
    
    public <T extends AbstractBrainCell> T get(String command){
        if(!brainCellMap.containsKey(command)){
            return knowledge.contains(command)? (T) knowledgeBrainCell : (T) AbstractBrainCell.NOT_FOUND_BRAIN_CELL;
        }
        return (T) brainCellMap.get(command);
    }
    
    public void putEmoticon(String key, String value){
        emoticon.put(key, value);
        brainCellMap.put(key, new EmoticonBrainCell(value));
    }

    public <T extends AbstractBrainCell> Set<Map.Entry<String, T>> findBrainCellByType(Class<T> type){
        return brainCellMap.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(type))
                .map(entry -> (Map.Entry<String, T>)entry)
                .collect(Collectors.toSet());
    }
}
