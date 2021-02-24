package com.github.frkator.test.results.diff;

import org.apache.maven.plugins.surefire.report.ReportTestSuite;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class ReportTestSuiteFacade {
    private final ReportTestSuite reportTestSuite;
    private final List<ReportTestCaseFacade> testCases;

    public ReportTestSuiteFacade(ReportTestSuite reportTestSuite) {
        this.reportTestSuite = reportTestSuite;
        testCases = reportTestSuite.getTestCases().stream().map(ReportTestCaseFacade::new).collect(Collectors.toUnmodifiableList());
    }

    public List<ReportTestCaseFacade> getTestCases() {
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

    @Override
    public String toString() {
        return reportTestSuite.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportTestSuiteFacade that = (ReportTestSuiteFacade) o;
        boolean isEqual = true;
        for (Method declaredMethod : ReportTestSuiteFacade.class.getDeclaredMethods()) {
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
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(
                stream(ReportTestSuiteFacade.class.getDeclaredMethods())
                .filter(Util::isEligible)
                .map(m -> {
                    try {
                        return m.invoke(this);
                    } catch (Exception e) {
                        throw new RuntimeException(m.getName(), e);
                    }
                }).toArray());
    }

}
