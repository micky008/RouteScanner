# RouteScanner
Scan all classes (with package) to find the rest routes,  
and build an html file to list all routes.

It's work with only Jax-rs 2.

Exemple:

```JAVA
        DocumentationConfig config = new DocumentationConfig();
        config.addPackage("com.msc.mypackage"); //it's recursive
        File result = new File("result.html");
        config.setHtmlOutputFile(result);
        Documentation doc = new Documentation(config);
        doc.fill();
        doc.print();
```
