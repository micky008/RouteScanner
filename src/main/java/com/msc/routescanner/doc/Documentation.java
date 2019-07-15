package com.msc.routescanner.doc;

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

    public Documentation(DocumentationConfig documentationConfig) throws NoPackageException, NoDocumentationConfigException {
        if (documentationConfig == null) {
            throw new NoDocumentationConfigException("DocumentationConfig is required");
        }
        if (documentationConfig.getPackages() == null || documentationConfig.getPackages().isEmpty()) {
            throw new NoPackageException("one [or more] package[s] is required");
        }
        this.docConfig = documentationConfig;
    }

    public Map<String, List<Route>> getRoutes() {
        return this.map;
    }

    public void go() {
        ReadPackage rp = new ReadPackage();

        for (String pkgName : this.docConfig.getPackages()) {
            for (Class clazz : rp.find(pkgName)) {
                ReadClass rc = new ReadClass(clazz);
                this.map.put(clazz.getSimpleName(), rc.getRoutes());
            }
        }
//        ReadClass rc = new ReadClass(AuditManagementResource.class);
//        for (Route r : rc.getRoutes()) {
//            System.out.println("-----");
//            System.out.println(r.getMethod());
//            System.out.println(r.getRoute());
//            System.out.println(r.getConsumes());
//            System.out.println(r.getInputs());
//            System.out.println(r.getProduces());
//            System.out.println(r.getResponse());
//            System.out.println("-----");
//        }
    }

}
