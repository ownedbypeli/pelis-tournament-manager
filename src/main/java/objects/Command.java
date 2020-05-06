package objects;

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
}