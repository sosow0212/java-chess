package chess;

import chess.controller.ChessGameController;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.ResultView;

public class Application {

    public static void main(String[] args) {
        ChessGameController chessGameController = new ChessGameController(
                new InputView(),
                new OutputView(),
                new ResultView(),
                new BoardService()
        );

        chessGameController.run();
    }
}
