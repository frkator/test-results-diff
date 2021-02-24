package com.github.frkator.test.results.diff;

import java.util.LinkedHashSet;
import java.util.Set;

public class SuiteReport {

    private final App app;

    public SuiteReport(App app) {
        this.app = app;
    }

    public void process() {


        Set<ReportTestSuiteFacade> onlyLeft = new LinkedHashSet<>(app.leftSet);
        Set<ReportTestSuiteFacade> onlyRight = new LinkedHashSet<>(app.rightSet);
        Set<ReportTestSuiteFacade> intersectionLeft = new LinkedHashSet<>(app.leftSet);
        Set<ReportTestSuiteFacade> intersectionRight = new LinkedHashSet<>(app.rightSet);

        onlyLeft.removeAll(new LinkedHashSet<>(app.rightSet));
        onlyRight.removeAll(app.leftSet);
        intersectionLeft.retainAll(new LinkedHashSet<>(app.rightSet));
        intersectionRight.retainAll(app.leftSet);

        if (intersectionLeft.size() != intersectionRight.size() || !intersectionLeft.equals(intersectionRight)) {
            throw new IllegalStateException("should not happen: intersection not commutative - retainAll did not result as expected");
        }

        if (onlyLeft.size() + intersectionLeft.size() != app.leftSet.size()) {
            throw new IllegalStateException("should not happen: removeAll did not result as expected");
        }

        if (onlyRight.size() + intersectionRight.size() != app.rightSet.size()) {
            throw new IllegalStateException("should not happen: removeAll did not result as expected");
        }

        if (app.settings.isShowLeft()) {
            app.printStream.println("only left");
            app.printStream.println(Util.toString(onlyLeft, ReportTestSuiteFacade::toString));
        }
        if (app.settings.isShowRight()) {
            app.printStream.println("only right");
            app.printStream.println(Util.toString(onlyRight, ReportTestSuiteFacade::toString));
        }
        if (app.settings.isShowCommon()) {
            app.printStream.println("common");
            app.printStream.println(Util.toString(intersectionLeft, ReportTestSuiteFacade::toString));
        }
    }
}
