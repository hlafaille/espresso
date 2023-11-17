package xyz.hlafaille.espresso.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Command {
    String name;
    String shortName;
    String helpText;
}