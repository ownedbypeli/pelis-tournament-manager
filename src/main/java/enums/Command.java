package enums;

import java.util.concurrent.ConcurrentMap;

public enum Command {
    GET_TOURNAMENT("!getTournament"),
    Register_Participant("!register");

  private final String command;

  Command(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

    public static Command getCommandFromString(String text) {
        for (Command command : Command.values()) {
            if (command.getCommand().equalsIgnoreCase(text)) {
                return command;
            }
        }
        return null;
    }
}