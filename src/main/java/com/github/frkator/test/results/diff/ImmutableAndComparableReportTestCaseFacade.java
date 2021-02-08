package com.github.frkator.test.results.diff;

import org.apache.maven.plugins.surefire.report.ReportTestCase;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImmutableAndComparableReportTestCaseFacade {
    
    private final ReportTestCase reportTestCase;

    public ImmutableAndComparableReportTestCaseFacade(ReportTestCase reportTestCase) {
        this.reportTestCase = reportTestCase;
    }

    public String getName() {
        return reportTestCase.getName();
    }


    public String getFullClassName() {
        return reportTestCase.getFullClassName();
    }


    public String getClassName() {
        return reportTestCase.getClassName();
    }

/*
    public float getTime() {
        return reportTestCase.getTime();
    }
*/
    public String getFullName() {
        return reportTestCase.getFullName();
    }


    public String getFailureMessage() {
        return reportTestCase.getFailureMessage();
    }


    public String getFailureType() {
        return reportTestCase.getFailureType();
    }

    public String getFailureErrorLine() {
        return reportTestCase.getFailureErrorLine();
    }


    public String getFailureDetail() {
        return reportTestCase.getFailureDetail();
    }

    public boolean isSuccessful() {
        return !reportTestCase.hasFailure() && !reportTestCase.hasError() && !reportTestCase.hasSkipped();
    }

    public boolean hasFailure() {
        return reportTestCase.hasFailure();
    }

    public boolean hasError() {
        return reportTestCase.hasError();
    }

    public boolean hasSkipped() {
        return reportTestCase.hasSkipped();
    }

    @Override
    public String toString() {
        return String.format("%s:%s",
                                reportTestCase.toString(),
                                isSuccessful() ? "passed" : getFailureType()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableAndComparableReportTestCaseFacade that = (ImmutableAndComparableReportTestCaseFacade) o;
        boolean isEqual = true;
        for (Method declaredMethod : ImmutableAndComparableReportTestCaseFacade.class.getDeclaredMethods()) {
            if (Util.isEligible(declaredMethod)) {
                try {
                    isEqual = isEqual && Objects.equals(
                            declaredMethod.invoke(this),
                            declaredMethod.invoke(that)
                    );
                }
                catch (Exception e) {
                    throw new RuntimeException(declaredMethod.getName(),e);
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
        List<Object> values = Arrays.asList(ImmutableAndComparableReportTestCaseFacade.class.getDeclaredMethods())
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
