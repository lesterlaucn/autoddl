package com.lesterlaucn.autoddl4j.parser.entity.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class ClasspathPackageScanner {

    private String basePackage;
    private ClassLoader cl;

    /**
     * Construct an instance and specify the base package it should scan.
     *
     * @param basePackage The base package to scan.
     */
    public ClasspathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();
    }

    /**
     * Construct an instance with base package and class loader.
     *
     * @param basePackage The base package to scan.
     * @param cl          Use this class load to locate the package.
     */
    public ClasspathPackageScanner(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     * @throws IOException
     */
    public List<String> getFullyQualifiedClassNameList() {
        log.info("开始扫描包{}下的所有类", basePackage);
        try {
            return doScan(basePackage, new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actually perform the scanning procedure.
     *
     * @param basePackage
     * @param nameList    A list to contain the result.
     * @return A list of fully qualified names.
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {
        // replace dots with splashes
        String splashPath = basePackage.replaceAll("\\.", "/");

        // get file path
        URL url = cl.getResource(splashPath);
        String filePath = StringUtil.getRootPath(url);

        // Get classes in that package.
        // If the web server unzips the jar file, then the classes will exist in the form of
        // normal file in the directory.
        // If the web server does not unzip the jar file, then classes will exist in jar file.
        List<String> names = null; // contains the name of the class file. e.g., Apple.class will be stored as "Apple"
        if (StringUtil.isJarFile(filePath)) {
            // jar file
            if (log.isDebugEnabled()) {
                log.debug("{} 是一个JAR包", filePath);
            }

            names = readFromJarFile(filePath, splashPath);
        } else {
            // directory
            if (log.isDebugEnabled()) {
                log.debug("{} 是一个目录", filePath);
            }

            names = StringUtil.readFromDirectory(filePath);
        }

        for (String name : names) {
            if (StringUtil.isClassFile(name)) {
                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {
                // this is a directory
                // check this directory for more classes
                // do recursive invocation
                doScan(basePackage + "." + name, nameList);
            }
        }

        if (log.isDebugEnabled()) {
            for (String n : nameList) {
                log.debug("找到{}", n);
            }
        }

        return nameList;
    }

    /**
     * Convert short class name to fully qualified name.
     * e.g., String -> java.lang.String
     */
    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtil.trimExtension(shortName));

        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("从JAR包中读取类: {}", jarPath);
        }

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && StringUtil.isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

}
