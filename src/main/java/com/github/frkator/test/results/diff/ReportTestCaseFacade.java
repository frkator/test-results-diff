package com.github.frkator.test.results.diff;

import org.apache.maven.plugins.surefire.report.ReportTestCase;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.stream;

public class ReportTestCaseFacade {

    private final ReportTestCase reportTestCase;

    public ReportTestCaseFacade(ReportTestCase reportTestCase) {
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
        ReportTestCaseFacade that = (ReportTestCaseFacade) o;
        boolean isEqual = true;
        for (Method declaredMethod : ReportTestCaseFacade.class.getDeclaredMethods()) {
            if (Util.isEligible(declaredMethod)) {
                try {
                    isEqual = isEqual && Objects.equals(
                            declaredMethod.invoke(this),
                            declaredMethod.invoke(that)
                    );
                } catch (Exception e) {
                    throw new RuntimeException(declaredMethod.getName(), e);
                }
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(
                stream(ReportTestCaseFacade.class.getDeclaredMethods())
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
