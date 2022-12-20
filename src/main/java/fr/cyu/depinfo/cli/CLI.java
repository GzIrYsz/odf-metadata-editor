package fr.cyu.depinfo.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Parameters;

import fr.cyu.depinfo.core.Core;
import fr.cyu.depinfo.filemanager.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.Callable;

@Command(name = "CLI", mixinStandardHelpOptions = true, description = "Print or modify the metata of an ODT file.")
public class CLI implements Callable<Integer> {

    @ArgGroup(multiplicity = "1")
    ModeChooser mc;

    static class ModeChooser {
        @ArgGroup(exclusive = false)
        DependantFileOptions dfo;

        @ArgGroup(exclusive = false)
        DependantDirOptions ddo;
    }

    static class DependantFileOptions {
        @Option(names = {"-f", "--file"}, description = "The file to use.", required = true)
        private String odt;

        @Option(names = "--title", description = "The new description of the file.")
        private String title;

        @Option(names = "--description", description = "The new description of the file.")
        private String description;
    }

    static class DependantDirOptions {
        @Option(names = {"-d", "--dir", "--directory"}, description = "The directory to look in", required = true)
        private String dir;

        @Option(names = {"-r", "--recursive"}, description = "Look into the directory recusively.")
        private boolean recursive;
    }

    @Override
    public Integer call() throws Exception {
        Core core = new Core();
        if (mc.dfo != null) {
            core.setOdtWPath(mc.dfo.odt);
            core.setOutDir();
        } else if (mc.ddo != null) {
            ArrayList<File> odtFiles = new ArrayList<>();
            FileManager.getODTInDir(odtFiles, mc.ddo.dir, mc.ddo.recursive);
            for (File file : odtFiles) {
                System.out.println(file.getPath());
            }
        } else {
            throw new Exception("fsdsdffd");
        }
        return 0;
    }

    public static void main(String... args) {
//        Core core = new Core();
//        try {
//            core.setOdtWPath("src/main/resources/odt_test_file.odt");
//            core.setOutDir();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        int exitCode = new CommandLine(new CLI()).execute(args);
        System.exit(exitCode);
    }
}
