package com.msc.routescanner.doc;

import com.msc.routescanner.doc.config.DocumentationConfig;
import com.msc.routescanner.doc.exception.NoDocumentationConfigException;
import com.msc.routescanner.doc.exception.NoPackageException;
import com.msc.routescanner.doc.freemarker.FreeMarkerConfiguration;
import freemarker.template.TemplateException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class Documentation {

    private final DocumentationConfig docConfig;
    private final Map<String, List<Route>> map = new HashMap<>();

    public Documentation(DocumentationConfig documentationConfig) throws NoPackageException, NoDocumentationConfigException, IOException {
        if (documentationConfig == null) {
            throw new NoDocumentationConfigException("DocumentationConfig is required");
        }
        if (documentationConfig.getPackages() == null || documentationConfig.getPackages().isEmpty()) {
            throw new NoPackageException("one [or more] package[s] is required");
        }
        if (documentationConfig.useCustomeTemplate() || documentationConfig.useHtmlFile()) {
            FreeMarkerConfiguration.init(documentationConfig);
        }
        this.docConfig = documentationConfig;
    }

    public Documentation(List<String> packages) throws NoPackageException {
        if (packages == null || packages.isEmpty()) {
            throw new NoPackageException("one [or more] package[s] is required");
        }
        docConfig = new DocumentationConfig();
        docConfig.setPackages(packages);
    }

    public Documentation(String packageName) throws NoPackageException {
        if (packageName == null || packageName.isEmpty()) {
            throw new NoPackageException("one [or more] package[s] is required");
        }
        docConfig = new DocumentationConfig();
        docConfig.setPackages(Arrays.asList(packageName));
    }

    public Map<String, List<Route>> getRoutes() {
        return this.map;
    }

    public void fill() {
        ReadPackage rp = new ReadPackage();
        for (String pkgName : this.docConfig.getPackages()) {
            for (Class clazz : rp.find(pkgName)) {
                ReadClass rc = new ReadClass(clazz);
                this.map.put(clazz.getSimpleName(), rc.getRoutes());
            }
        }
    }

    public void print() throws IOException, TemplateException {
        if (this.docConfig.useHtmlFile() || this.docConfig.useCustomeTemplate()) {
            FreeMarkerConfiguration fmc = new FreeMarkerConfiguration();
            fmc.putMap(map);
            String result = fmc.template();
            if (this.docConfig.useHtmlFile()) {
                FileWriter fw = new FileWriter(this.docConfig.getHtmlOutputFile());
                fw.write(result);
                fw.flush();
                fw.close();
            } else {
                System.out.println(result);
            }
        } else {
            String headRoute = "";
            for (String key : this.map.keySet()) {
                headRoute = "";
                System.out.println("----------------------------------------");
                for (Route route : this.map.get(key)) {
                    if (route.isIsHead()) {
                        System.out.println(route.getResponse());
                        System.out.println(route.getRoute());
                        System.out.println("########################################");
                        headRoute = route.getRoute();
                        continue;
                    }
                    System.out.println(route.getMethod());
                    if (route.getProduces() != null && !route.getProduces().isEmpty()) {
                        System.out.println("Produces : " + Arrays.toString(route.getProduces().toArray()));
                    }
                    if (route.getConsumes() != null && !route.getConsumes().isEmpty()) {
                        System.out.println("Consumes : " + Arrays.toString(route.getConsumes().toArray()));
                    }
                    System.out.print(route.getResponse() + " " + headRoute);
                    if (route.getRoute() != null){
                        System.out.print(route.getResponse() + " " + headRoute);
                    }
                    if (route.getInputs() != null && !route.getInputs().isEmpty()) {
                        System.out.print(" args : " + Arrays.toString(route.getInputs().toArray()));
                    }
                    System.out.println();
                    System.out.println("----------------------------------------");
                }
            }
        }
    }

}
