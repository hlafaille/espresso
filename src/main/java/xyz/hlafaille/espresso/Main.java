package xyz.hlafaille.espresso;

import xyz.hlafaille.eap.EspressoArgumentParser;
import xyz.hlafaille.eap.exception.*;
import xyz.hlafaille.espresso.eap.project.ProjectContainer;


public class Main {
    public static void main(String[] args) throws EapCommandModifierNotFoundException, EapCommandNotFoundException,
            EapSubcommandNotFoundException, EapCommandNotSpecifiedException, EapMalformedCommandModifierException,
            EapMissingSubcommandException, EapDuplicateCommandContainerException {
        // create our eap instance
        EspressoArgumentParser espressoArgumentParser = new EspressoArgumentParser(
                "Espresso",
                "Wake up your workflow with Espresso"
        );

        // add our command containers
        espressoArgumentParser.addCommandContainer(new ProjectContainer());


        // parse :)
        espressoArgumentParser.parse(args);
    }
}