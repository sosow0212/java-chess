package chess.view;

import chess.domain.commnad.Command;
import chess.domain.commnad.RoomCommand;
import java.util.Scanner;

public class InputView {

    private final static Scanner input = new Scanner(System.in);

    public Command readGameCommand() {
        try {
            String gameCommand = input.nextLine();
            return Command.from(gameCommand);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readGameCommand();
        }
    }

    public RoomCommand readEnterGame() {
        try {
            System.out.println("1 : 체스 방 만들기, 2 : 체스 게임방 입장하기");
            String command = input.nextLine();
            return RoomCommand.from(command);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readEnterGame();
        }
    }

    public int readRoomNumber() {
        try {
            System.out.println("방 번호를 입력해주세요.");
            return input.nextInt();
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readRoomNumber();
        }
    }
}
