package org.junit.runner;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JavaDescription {

    public static Description createSuiteDescription(final Class<?> testClass) {
        JavaTestClass javaTestClass = createJavaTestClassAnnotation(testClass);
        List<Annotation> annotations = new ArrayList<Annotation>();
        Collections.addAll(annotations, testClass.getAnnotations());
        annotations.add(javaTestClass);

        return new Description(testClass, testClass.getName(), annotations.toArray(new Annotation[]{}));
    }

    public static Description createTestDescription(Class<?> testClass, String name, Annotation... annotations) {
        JavaTestClass javaTestClass = createJavaTestClassAnnotation(testClass);
        JavaTestMethod javaTestMethod = createJavaTestMethodAnnotation(name);

        List<Annotation> newAnnotations = new ArrayList<Annotation>();
        Collections.addAll(newAnnotations, annotations);
        newAnnotations.add(javaTestClass);
        newAnnotations.add(javaTestMethod);

        return new Description(testClass, formatDisplayName(name, testClass.getName()), newAnnotations.toArray(new Annotation[]{}));
    }

    private static String formatDisplayName(String name, String className) {
        return String.format("%s(%s)", name, className);
    }

    private static JavaTestClass createJavaTestClassAnnotation(final Class<?> testClass) {
        return new JavaTestClass() {
            public Class<?> getValue() {
                return testClass;
            }

            public Class<? extends Annotation> annotationType() {
                return JavaTestClass.class;
            }
        };
    }

    private static JavaTestMethod createJavaTestMethodAnnotation(final String methodName) {
        return new JavaTestMethod() {
            public String getValue() {
                return methodName;
            }

            public Class<? extends Annotation> annotationType() {
                return JavaTestMethod.class;
            }
        };
    }
}
