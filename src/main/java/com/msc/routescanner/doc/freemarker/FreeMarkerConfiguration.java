package com.msc.routescanner.doc.freemarker;

import com.msc.routescanner.doc.config.DocumentationConfig;
import com.msc.routescanner.doc.Route;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class FreeMarkerConfiguration {

    private static Configuration confFm;
    private Map<String, List<Route>> map;
    private static DocumentationConfig dc;

    public static void init(DocumentationConfig dc) throws IOException {
        FreeMarkerConfiguration.dc = dc;
        confFm = new Configuration(Configuration.VERSION_2_3_28);
        TemplateLoader templateLoader = new ClassTemplateLoader(FreeMarkerConfiguration.class, "/");
        if (dc.useCustomeTemplate()) {
            templateLoader = new FileTemplateLoader(dc.getCustomeTemplate().getParentFile());
        }
        confFm.setTemplateLoader(templateLoader);
        confFm.setDefaultEncoding("UTF-8");
        confFm.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        confFm.setLogTemplateExceptions(true);
        confFm.setWrapUncheckedExceptions(true);
    }

    public void putMap(Map<String, List<Route>> map) {
        this.map = map;
    }

    public String template() throws IOException, TemplateException {
        if (map == null || map.isEmpty()){
            throw new TemplateException("the map couldn't be null or empty", null);
        }
        Map<String, Object> root = new HashMap<>();
        root.put("myMap", map);
        Template temp = null;
        if (dc.useCustomeTemplate()) {
            temp = confFm.getTemplate(dc.getCustomeTemplate().getName());
        } else {
            temp = confFm.getTemplate("template.html");
        }
        StringWriter sw = new StringWriter();
        temp.process(root, sw);
        return sw.toString();
    }

    //myMap
}
