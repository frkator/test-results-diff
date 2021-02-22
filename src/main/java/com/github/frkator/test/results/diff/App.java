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
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public final Arguments arguments;
    public final PrintStream printStream;
    public final Settings settings;
    public final Set<ImmutableAndComparableReportTestSuiteFacade> leftSet;
    public final Set<ImmutableAndComparableReportTestSuiteFacade> rightSet;


    public App(Arguments arguments, PrintStream printStream, Settings settings) throws Exception {
        this.arguments = arguments;
        this.printStream = printStream;
        this.settings = settings;
        List<File> leftDirectories = findReportDirectories(arguments.getLeftPath());
        List<File> rightDirectories = findReportDirectories(arguments.getRightPath());

        SurefireReportParser leftReportParser = new SurefireReportParser(leftDirectories, Locale.getDefault(), new PrintStreamLogger(new PrintStream(System.out)));
        SurefireReportParser rightReportParser = new SurefireReportParser(rightDirectories, Locale.getDefault(), new PrintStreamLogger(new PrintStream(System.out)));

        List<ReportTestSuite>leftReportTestSuites = Collections.unmodifiableList(leftReportParser.parseXMLReportFiles());
        List<ReportTestSuite> rightReportTestSuites = Collections.unmodifiableList(rightReportParser.parseXMLReportFiles());

        this.leftSet = Collections.unmodifiableSet((Set<? extends ImmutableAndComparableReportTestSuiteFacade>) leftReportTestSuites.stream().map(ImmutableAndComparableReportTestSuiteFacade::new).collect(Collectors.toCollection(LinkedHashSet::new)));
        this.rightSet = Collections.unmodifiableSet((Set<? extends ImmutableAndComparableReportTestSuiteFacade>) rightReportTestSuites.stream().map(ImmutableAndComparableReportTestSuiteFacade::new).collect(Collectors.toCollection(LinkedHashSet::new)));


        if (leftReportTestSuites.size() != leftSet.size()) {
            throw new IllegalStateException();
        }
        if (rightReportTestSuites.size() != rightSet.size()) {
            throw new IllegalStateException();
        }
    }

    public static void main(String[] args) throws Exception {
        Arguments arguments = new Arguments(args);
        Settings settings = new Settings();
        App app = new App(arguments,System.out,settings);
        CaseReport caseReport = new CaseReport(app);
        caseReport.process();
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

}
