package com.msc.routescanner.doc;

import java.io.File;
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

    public File getHtmlFile() {
        return htmlFile;
    }

    public void setHtmlFile(File htmlFile) {
        this.htmlFile = htmlFile;
    }

    public File getCustomeTemplate() {
        return customeTemplate;
    }

    public void setCustomeTemplate(File customeTemplate) {
        this.customeTemplate = customeTemplate;
    }

    

}
