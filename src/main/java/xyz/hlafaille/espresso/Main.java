package xyz.hlafaille.espresso;


import xyz.hlafaille.eap.EspressoArgumentParser;
import xyz.hlafaille.eap.exception.*;
import xyz.hlafaille.espresso.cli.ArgumentParser;
import xyz.hlafaille.espresso.cli.Command;
import xyz.hlafaille.espresso.cli.handler.InitCommandHandler;
import xyz.hlafaille.espresso.cli.handler.InstallCommandHandler;
import xyz.hlafaille.espresso.cli.handler.PullCommandHandler;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;
import xyz.hlafaille.espresso.project.ProjectManager;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws EapCommandModifierNotFoundException, EapCommandNotFoundException,
            EapSubcommandNotFoundException, EapCommandNotSpecifiedException, EapMalformedCommandModifierException,
            EapMissingSubcommandException {
        EspressoArgumentParser espressoArgumentParser = new EspressoArgumentParser(
                "Espresso",
                "Wake up your workflow with Espresso"
        );
        espressoArgumentParser.parse(args);
    }
}