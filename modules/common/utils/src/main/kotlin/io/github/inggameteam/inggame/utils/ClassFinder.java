package io.github.inggameteam.inggame.utils;

import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassCriteria;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import org.burningwave.core.io.PathHelper;

import java.util.Collection;
import java.util.function.Function;

public class ClassFinder {

    public static Collection<Class<?>> find(Function<Class<?>, Boolean> filter) {
        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        PathHelper pathHelper = componentSupplier.getPathHelper();
        ClassHunter classHunter = componentSupplier.getClassHunter();
        System.out.println(pathHelper.getMainClassPaths());
        System.out.println(pathHelper.getMainClassPaths());
        System.out.println(pathHelper.getMainClassPaths());
        System.out.println(pathHelper.getMainClassPaths());
        System.out.println(pathHelper.getMainClassPaths());
        SearchConfig searchConfig = SearchConfig.forPaths(
            //Here you can add all absolute path you want:
            //both folders, zip and jar will be recursively scanned.
            //For example you can add: "C:\\Users\\user\\.m2"
            //With the line below the search will be executed on runtime Classpaths
            pathHelper.getMainClassPaths()
        ).by(
            ClassCriteria.create().allThoseThatMatch(filter::apply)
        );

        try (ClassHunter.SearchResult searchResult = classHunter.findBy(searchConfig)) {
    
            //If you need all annotaded methods unconment this
            //searchResult.getMembersFlatMap().values();
    
            return searchResult.getClasses();
        }
    }
}
