package com.msc.routescanner.doc.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michael
 */
public class DocumentationConfig {

    private List<String> packages;
    private File htmlFile;
    private File customeTemplate;

    public boolean useHtmlFile() {
        return this.htmlFile != null;
    }

    public boolean useCustomeTemplate() {
        return this.customeTemplate != null;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public void addPackage(String pkg){
        if (this.packages == null){
            this.packages = new ArrayList<>();
        }
        this.packages.add(pkg);
    }

    public File getHtmlOutputFile() {
        return htmlFile;
    }

    public void setHtmlOutputFile(File htmlFile) {
        this.htmlFile = htmlFile;
    }

    public File getCustomeTemplate() {
        return customeTemplate;
    }

    public void setCustomeTemplate(File customeTemplate) {
        this.customeTemplate = customeTemplate;
    }

    

}
