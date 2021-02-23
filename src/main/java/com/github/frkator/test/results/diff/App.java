/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.frkator.test.results.diff;

import org.apache.maven.plugin.surefire.log.api.PrintStreamLogger;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.frkator.test.results.diff.Preconditions.checkSizes;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toCollection;

public class App {

    private static final String SIZE_ERR_MSG = "Suite size doesn't match expected value";
    public final Arguments arguments;
    public final PrintStream printStream;
    public final Settings settings;
    public final Set<ImmutableAndComparableReportTestSuiteFacade> leftSet;
    public final Set<ImmutableAndComparableReportTestSuiteFacade> rightSet;

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments(args);
        Settings settings = new Settings();
        App app = new App(arguments, System.out, settings);
        CaseReport caseReport = new CaseReport(app);
        caseReport.process();
    }

    public App(Arguments arguments, PrintStream printStream, Settings settings) throws Exception {
        this.arguments = arguments;
        this.printStream = printStream;
        this.settings = settings;
        List<File> leftDirectories = findReportDirectories(arguments.getLeftPath());
        List<File> rightDirectories = findReportDirectories(arguments.getRightPath());

        SurefireReportParser leftReportParser = new SurefireReportParser(leftDirectories, Locale.getDefault(), new PrintStreamLogger(new PrintStream(System.out)));
        SurefireReportParser rightReportParser = new SurefireReportParser(rightDirectories, Locale.getDefault(), new PrintStreamLogger(new PrintStream(System.out)));

        List<ReportTestSuite> leftReportTestSuites = toSorted(leftReportParser.parseXMLReportFiles());
        List<ReportTestSuite> rightReportTestSuites = toSorted(rightReportParser.parseXMLReportFiles());

        this.leftSet = toFacade(leftReportTestSuites);
        this.rightSet = toFacade(rightReportTestSuites);

        checkSizes(leftReportTestSuites, leftSet, SIZE_ERR_MSG);
        checkSizes(rightReportTestSuites, rightSet, SIZE_ERR_MSG);
    }

    private List<File> findReportDirectories(Path path) throws IOException {
        return Files
                .walk(path)
                .filter(p ->
                        p.getFileName().toString().matches("^.*[xX][mM][lL]$") &&
                                p.getFileName().toString().matches("^[tT][eE][sS][tT][-].*$")
                )
                .map(p -> p.getParent().toFile())
                .distinct()
                .collect(Collectors.toList());
    }

    private Set<ImmutableAndComparableReportTestSuiteFacade> toFacade(List<ReportTestSuite> reportTestSuites) {
        LinkedHashSet<ImmutableAndComparableReportTestSuiteFacade> facades = reportTestSuites.stream().
                map(ImmutableAndComparableReportTestSuiteFacade::new)
                .collect(toCollection(LinkedHashSet::new));

        return unmodifiableSet(facades);
    }

    private List<ReportTestSuite> toSorted(List<ReportTestSuite> reportTestSuites) {
        reportTestSuites.sort(Comparator.comparing(ReportTestSuite::getFullClassName));

        return unmodifiableList(reportTestSuites);
    }

}
