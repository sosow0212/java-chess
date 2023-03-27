package chess.domain.commnad;

import chess.exception.CommandMessage;

public enum LoadGameCommand {

    NEW_GAME("1"),
    SAVED_GAME("2");

    private final String command;

    LoadGameCommand(final String command) {
        this.command = command;
    }

    public static LoadGameCommand from(final String command) {
        validate(command);
        
        if (command.equals("1")) {
            return NEW_GAME;
        }

        if (command.equals("2")) {
            return SAVED_GAME;
        }

        throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다.");
    }

    private static void validate(final String command) {
        if (!(command.equals("1") || command.equals("2"))) {
            throw new IllegalArgumentException(CommandMessage.STATUS_COMMAND_INVALID.getMessage());
        }
    }

    public boolean isNewGame() {
        return this == NEW_GAME;
    }

    public boolean isSavedGame() {
        return this == SAVED_GAME;
    }
}
