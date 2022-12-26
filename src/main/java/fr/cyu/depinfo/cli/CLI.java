package fr.cyu.depinfo.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

import fr.cyu.depinfo.core.Core;
import fr.cyu.depinfo.filemanager.FileManager;
import fr.cyu.depinfo.xmlprocessor.MetadataExtractor;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

@Command(name = "CLI", mixinStandardHelpOptions = true, description = "Print or modify the metata of an ODT file.")
public class CLI implements Callable<Integer> {

    @ArgGroup(multiplicity = "1")
    ModeChooser mc;

    public static class ModeChooser {
        @ArgGroup(exclusive = false)
        private DependantFileOptions dfo;

        @ArgGroup(exclusive = false)
        private DependantDirOptions ddo;

        public DependantFileOptions getDfo() {
            return this.dfo;
        }

        public DependantDirOptions getDdo() {
            return this.ddo;
        }
    }

    public static class DependantFileOptions {
        @Option(names = {"-f", "--file"}, description = "The file to use.", required = true)
        private String odt;

        @Option(names = {"-o", "--output"}, description = "The output file.")
        private String out;

        @Option(names = "--title", description = "The new description of the file.")
        private String title;

        @Option(names = "--description", description = "The new description of the file.")
        private String description;

        @Option(names = "--author", description = "The new author of the file.")
        private String author;

        @Option(names = "--subject", description = "The new subject of the file.")
        private String subject;

        @Option(names = "--keywords", description = "The new keywords of the file.")
        private String keywords;

        public String getOdt() {
            return this.odt;
        }

        public HashMap<String, String> getEditables() {
            HashMap<String, String> output = new HashMap<>();
            output.put(MetadataExtractor.TITLE, title);
            output.put(MetadataExtractor.DESCRIPTION, description);
            output.put(MetadataExtractor.AUTHOR, author);
            output.put(MetadataExtractor.SUBJECT, subject);
            output.put(MetadataExtractor.KEYWORD, keywords);
            return output;
        }

        public boolean editableAreNull() {
            return Stream.of(title, description, author, subject, keywords).allMatch(Objects::isNull);
        }
    }

    public static class DependantDirOptions {
        @Option(names = {"-d", "--dir", "--directory"}, description = "The directory to look in", required = true)
        private String dir;

        @Option(names = {"-r", "--recursive"}, description = "Look into the directory recusively.")
        private boolean recursive;
    }

    @Override
    public Integer call() throws Exception {
        Core core = new Core();
        if (mc.dfo != null && mc.ddo == null) {
            core.generate(mc);
            if (mc.dfo.editableAreNull()) {
                System.out.println(core.getAllMetadata());
            } else {
                core.setMetadata(mc.dfo.getEditables());
                core.saveODT();
            }
            FileManager.deleteDir(core.getOutDir(), core.getOutDir().listFiles());
        } else if (mc.ddo != null && mc.dfo == null) {
            ArrayList<File> odtFiles = new ArrayList<>();
            FileManager.getODTInDir(odtFiles, mc.ddo.dir, mc.ddo.recursive);
            if (odtFiles.isEmpty()) {
                System.out.println("Aucun fichier ODT present !");
            }
            for (File file : odtFiles) {
                System.out.println(file.getPath());
            }
        } else {
            throw new Exception("fsdsdffd");
        }
        return 0;
    }

    public static void main(String... args) {
        IExecutionExceptionHandler errorHandler = new IExecutionExceptionHandler() {
            public int handleExecutionException(Exception ex,
                                                CommandLine commandLine,
                                                ParseResult parseResult) {
                commandLine.getErr().println(ex.getMessage());
                return commandLine.getCommandSpec().exitCodeOnExecutionException();
            }
        };

        int exitCode = new CommandLine(new CLI())
                .setExecutionExceptionHandler(errorHandler)
                .execute(args);
        System.exit(exitCode);
    }
}
