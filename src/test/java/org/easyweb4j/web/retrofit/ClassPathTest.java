package org.easyweb4j.web.retrofit;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.io.IOException;
import org.testng.annotations.Test;

public class ClassPathTest {
  @Test
  public void recursive() throws IOException {
    ClassPath classPath = ClassPath.from(getClass().getClassLoader());
    ImmutableSet<ClassInfo> topLevelClassesRecursive = classPath
      .getTopLevelClassesRecursive("org.easyweb4j.web.retrofit");
    for (ClassInfo classInfo : topLevelClassesRecursive) {
      System.out.println(classInfo.getPackageName());
      System.out.println(classInfo.getSimpleName());
      System.out.println(classInfo.getName());
      System.out.println(classInfo.getResourceName());
    }
  }

}
