package org.junit.runner;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaDescriptionTest {

    private static class SampleTest {
        @Test
        public void sampleTest() {

        }
    }

    @Test
    public void shouldCreateSuiteDescription() {
        Description description = JavaDescription.createSuiteDescription(SampleTest.class);
        assertThat(description.getAnnotations().size(), is(1));
        JavaTestClass javaTestClassAnnotation = (JavaTestClass) description.getAnnotations().iterator().next();
        assertThat(javaTestClassAnnotation.getValue(), CoreMatchers.<Class>equalTo(SampleTest.class));
    }


    @Test
    public void shouldCreateTestDescriptionWithAnnotationSupplied() {
        Ignore ignore = new Ignore() {
            public String value() {
                return "sampleTest";
            }

            public Class<? extends Annotation> annotationType() {
                return Ignore.class;
            }
        };

        Description description = JavaDescription.createTestDescription(SampleTest.class, "Sample Test", ignore);
        assertThat(description.getAnnotations().size(), is(3));

        assertThat(description.getAnnotation(JavaTestMethod.class).getValue(), is("Sample Test"));
    }

    @Test
    public void shouldCreateTestDescription() {
        Description description = JavaDescription.createTestDescription(SampleTest.class, "Sample Test");
        assertThat(description.getAnnotations().size(), is(2));
    }
}
