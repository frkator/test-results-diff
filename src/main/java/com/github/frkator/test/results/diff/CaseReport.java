package com.github.frkator.test.results.diff;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CaseReport {
    private final App app;

    public CaseReport(App app) {
        this.app = app;
    }

    private Map<ImmutableAndComparableReportTestCaseFacade,ImmutableAndComparableReportTestSuiteFacade> suitesPerCaseIndex(Set<ImmutableAndComparableReportTestSuiteFacade> input) {
        return input.stream().flatMap( suite ->
                suite.getTestCases().stream().map(testCase -> Map.entry(testCase, suite))
        ).collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b) -> a,
                        LinkedHashMap::new
                )
        );
    }

    private Map<ImmutableAndComparableReportTestSuiteFacade, List<ImmutableAndComparableReportTestCaseFacade>> groupCasePerSuite(Set<ImmutableAndComparableReportTestCaseFacade> input, Map<ImmutableAndComparableReportTestCaseFacade, ImmutableAndComparableReportTestSuiteFacade>  index) {
        return input.stream().collect(Collectors.groupingBy(index::get, LinkedHashMap::new, Collectors.toList()));
    }

    public void process() {
        Map<ImmutableAndComparableReportTestCaseFacade, ImmutableAndComparableReportTestSuiteFacade> leftInput = suitesPerCaseIndex(app.leftSet);
        Map<ImmutableAndComparableReportTestCaseFacade, ImmutableAndComparableReportTestSuiteFacade> rightInput = suitesPerCaseIndex(app.rightSet);

        Set<ImmutableAndComparableReportTestCaseFacade> onlyLeft = new LinkedHashSet<>(leftInput.keySet());
        Set<ImmutableAndComparableReportTestCaseFacade> onlyRight = new LinkedHashSet<>(rightInput.keySet());
        Set<ImmutableAndComparableReportTestCaseFacade> intersectionLeft = new LinkedHashSet<>(leftInput.keySet());
        Set<ImmutableAndComparableReportTestCaseFacade> intersectionRight = new LinkedHashSet<>(rightInput.keySet());

        onlyLeft.removeAll(new LinkedHashSet<>(rightInput.keySet()));
        onlyRight.removeAll(leftInput.keySet());
        intersectionLeft.retainAll(new LinkedHashSet<>(rightInput.keySet()));
        intersectionRight.retainAll(leftInput.keySet());

        if (intersectionLeft.size() != intersectionRight.size() || !intersectionLeft.equals(intersectionRight)) {
            throw new IllegalStateException("should not happen: intersection not commutative - retainAll did not result as expected");
        }

        if (onlyLeft.size() + intersectionLeft.size() != leftInput.size()) {
            throw new IllegalStateException("should not happen: removeAll did not result as expected");
        }

        if (onlyRight.size() + intersectionRight.size() != rightInput.size()) {
            throw new IllegalStateException("should not happen: removeAll did not result as expected");
        }

        if (app.settings.isShowLeft()) {
            app.printStream.println("only left");
            app.printStream.println(casesGroupedPerSuiteToString(onlyLeft, leftInput, app.settings.isStatusFilteringLeft(),app.settings.getLeftStatusFilter()));
        }
        if (app.settings.isShowRight()) {
            app.printStream.println("only right");
            app.printStream.println(casesGroupedPerSuiteToString(onlyRight, rightInput, app.settings.isStatusFilteringRight(),app.settings.getRightStatusFilter()));
        }
        if (app.settings.isShowCommon()) {
            app.printStream.println("common");
            var sum = new LinkedHashMap<ImmutableAndComparableReportTestCaseFacade, ImmutableAndComparableReportTestSuiteFacade>();
            sum.putAll(rightInput);
            sum.putAll(leftInput);
            app.printStream.println(casesGroupedPerSuiteToString(intersectionLeft, sum, app.settings.isStatusFilteringCommon(),app.settings.getCommonStatusFilter()));
        }
    }

    private String casesGroupedPerSuiteToString(Set<ImmutableAndComparableReportTestCaseFacade> input, Map<ImmutableAndComparableReportTestCaseFacade, ImmutableAndComparableReportTestSuiteFacade> index, boolean filteringOn, boolean filter) {
        Map<ImmutableAndComparableReportTestSuiteFacade, List<ImmutableAndComparableReportTestCaseFacade>> grouped = groupCasePerSuite(input, index);
        StringWriter sw = new StringWriter();
        for (ImmutableAndComparableReportTestSuiteFacade suite : grouped.keySet()) {
            final LinkedHashSet<ImmutableAndComparableReportTestCaseFacade> resultSet = new LinkedHashSet<>(grouped.get(suite));
            if (resultSet.size() != grouped.get(suite).size()) {
                throw new IllegalStateException("should not happen: grouping logic error");
            }
            if (filteringOn) {
                resultSet.removeAll(
                    resultSet.stream()
                        .filter(testCase -> testCase.isSuccessful() != filter)
                        .collect(Collectors.toSet())
                );
            }
            if (!resultSet.isEmpty()) {
                sw.write(String.format("%s%n%s%n",
                        suite.toString(),
                        Util.toString(
                                resultSet,
                                ImmutableAndComparableReportTestCaseFacade::toString
                        )
                        )
                );
            }
        }
        return sw.toString();
    }
}
