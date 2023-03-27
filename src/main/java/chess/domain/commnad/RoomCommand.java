package chess.domain.commnad;

import chess.exception.CommandMessage;

public enum RoomCommand {

    CREATE_GAME("1"),
    ENTER_GAME("2");

    private final String command;

    RoomCommand(final String command) {
        this.command = command;
    }

    public static RoomCommand from(final String command) {
        validate(command);
        if (command.equals("1")) {
            return CREATE_GAME;
        }

        if (command.equals("2")) {
            return ENTER_GAME;
        }

        throw new IllegalArgumentException("잘못된 명령어를 입력하셨습니다.");
    }

    private static void validate(final String command) {
        if (!(command.equals("1") || command.equals("2"))) {
            throw new IllegalArgumentException(CommandMessage.STATUS_COMMAND_INVALID.getMessage());
        }
    }

    public boolean isCreateGame() {
        return this == CREATE_GAME;
    }

    public boolean isEnterGame() {
        return this == ENTER_GAME;
    }
}
