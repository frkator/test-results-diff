package com.github.frkator.test.results.diff;

import org.apache.maven.plugins.surefire.report.ReportTestSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImmutableAndComparableReportTestSuiteFacade {
    private final ReportTestSuite reportTestSuite;
    private final List<ImmutableAndComparableReportTestCaseFacade> testCases;

    public ImmutableAndComparableReportTestSuiteFacade(ReportTestSuite reportTestSuite) {
        this.reportTestSuite = reportTestSuite;
        testCases = reportTestSuite.getTestCases().stream().map(ImmutableAndComparableReportTestCaseFacade::new).collect(Collectors.toUnmodifiableList());
    }

    public List<ImmutableAndComparableReportTestCaseFacade> getTestCases() {
        return testCases;
    }

    public int getNumberOfErrors() {
        return reportTestSuite.getNumberOfErrors();
    }


    public int getNumberOfFailures() {
        return reportTestSuite.getNumberOfFailures();
    }


    public int getNumberOfSkipped() {
        return reportTestSuite.getNumberOfSkipped();
    }



    public int getNumberOfFlakes() {
        return reportTestSuite.getNumberOfFlakes();
    }


    public int getNumberOfTests() {
        return  reportTestSuite.getNumberOfTests();
    }


    public String getName() {
        return reportTestSuite.getName();
    }


    public String getFullClassName() {
        return reportTestSuite.getFullClassName();
    }


    public String getPackageName() {
        return reportTestSuite.getPackageName();
    }

/*
    public float getTimeElapsed() {
        return reportTestSuite.getTimeElapsed();
    }
*/
    @Override
    public String toString() {
        return reportTestSuite.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableAndComparableReportTestSuiteFacade that = (ImmutableAndComparableReportTestSuiteFacade) o;
        boolean isEqual = true;
        for (Method declaredMethod : ImmutableAndComparableReportTestSuiteFacade.class.getDeclaredMethods()) {
            if (Util.isEligible(declaredMethod)) {
                try {
                    isEqual = isEqual && Objects.equals(
                            declaredMethod.invoke(this),
                            declaredMethod.invoke(that)
                    );
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                continue;
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        List<Object> values = Arrays.asList(ImmutableAndComparableReportTestSuiteFacade.class.getDeclaredMethods())
                .stream()
                .filter(Util::isEligible)
                .map(m -> {
                    try {
                        return m.invoke(this);
                    } catch (Exception e) {
                        throw new RuntimeException(m.getName(),e);
                    }
                }).collect(Collectors.toList());
        return Arrays.hashCode(values.toArray());
    }

}
