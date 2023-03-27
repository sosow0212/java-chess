package chess.controller;

import chess.domain.commnad.Command;
import chess.domain.commnad.RoomCommand;
import chess.domain.game.ChessGame;
import chess.dto.BoardResultDto;
import chess.dto.BoardSaveDto;
import chess.dto.ChessGameResponseDto;
import chess.dto.GameScoreResultDto;
import chess.factory.BoardFactory;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.ResultView;
import java.util.List;
import java.util.Scanner;

public class ChessGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ResultView resultView;
    private final BoardService boardService;

    public ChessGameController(final InputView inputView,
                               final OutputView outputView,
                               final ResultView resultView,
                               final BoardService boardService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.resultView = resultView;
        this.boardService = boardService;
    }

    public void ready() {
        List<Integer> roomNumbers = boardService.findAllRoomNumbers();

        RoomCommand roomCommand = inputView.readEnterGame();

        if (roomCommand.isCreateGame()) {
            ChessGame chessGame = new ChessGame(BoardFactory.createBoard(), true);
            outputView.printStartMessage();
            playChess(chessGame, roomNumbers.size() + 1);
            resultView.printGameEnd();
        }

        if (roomCommand.isEnterGame()) {
            outputView.printCreatedRoomNumbers(roomNumbers);
            int roomNumber = inputView.readRoomNumber();

            outputView.printStartMessage();
            ChessGameResponseDto chessGameResponseDto = boardService.findBoardById(roomNumber);
            BoardResultDto boardResultDto = boardService.findBoardById(roomNumber).getBoardResultDto();

            outputView.printBoard(boardResultDto.getPieces());
            ChessGame chessGame = new ChessGame(BoardFactory.createFromDto(boardResultDto),
                    chessGameResponseDto.isLowerTeamTurn());

            playChess(chessGame, roomNumber);
            resultView.printGameEnd();
        }
    }

    private void playChess(ChessGame chessGame, final int nowRoomNumber) {
        Command command = inputView.readGameCommand();

        while (!isGameEndCase(chessGame, command, nowRoomNumber)) {
            chessGame = checkCreateNewGame(chessGame, command);

            checkMovePiece(chessGame, command);
            outputView.printBoard(BoardResultDto.toDto(chessGame.getBoard()).getPieces());

            if (isGameDone(chessGame, nowRoomNumber)) {
                break;
            }

            command = inputView.readGameCommand();
        }
    }

    private boolean isGameEndCase(final ChessGame chessGame, final Command command, final int nowRoomNumber) {
        if (isGameEnd(chessGame, command)) {
            resultView.printGameEndWithSaving();
            if (boardService.isEmptyById(nowRoomNumber)) {
                boardService.delete(nowRoomNumber);
                boardService.save(BoardSaveDto.toDto(nowRoomNumber, chessGame));
            }
            if (!boardService.isEmptyById(nowRoomNumber)) {
                boardService.update(BoardSaveDto.toDto(nowRoomNumber, chessGame));
            }
            return true;
        }

        return false;
    }

    private boolean isGameEnd(final ChessGame chessGame, final Command command) {
        return isCommandStatus(chessGame, command) || command.isGameStop();
    }

    private boolean isCommandStatus(final ChessGame chessGame, final Command command) {
        if (command.isStatus()) {
            resultView.printScore(GameScoreResultDto.toDto(chessGame));
            resultView.printWinner(GameScoreResultDto.toDto(chessGame));
            return true;
        }

        return false;
    }

    private ChessGame checkCreateNewGame(final ChessGame chessGame, final Command command) {
        if (command.isCreateNewGame()) {
            chessGame.initGame();
        }

        return chessGame;
    }

    private boolean isGameDone(final ChessGame chessGame, final int nowRoomNumber) {
        if (isKingDead(chessGame)) {
            resultView.printGameEndWithDeleting();
            boardService.delete(nowRoomNumber);
            return true;
        }

        return false;
    }

    private boolean isKingDead(final ChessGame chessGame) {
        if (chessGame.isKingDead() && chessGame.isUpperTeamWin()) {
            resultView.printWinnerIsUpperTeam();
            return true;
        }

        if (chessGame.isKingDead() && !chessGame.isUpperTeamWin()) {
            resultView.printWinnerIsLowerTeam();
            return true;
        }

        return false;
    }

    private void checkMovePiece(final ChessGame chessGame, final Command command) {
        if (!command.isMove()) {
            return;
        }

        try {
            chessGame.move(command.findSelectedPiece(), command.findDestination());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
