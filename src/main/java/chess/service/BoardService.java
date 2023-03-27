package chess.service;

import chess.dao.BoardDao;
import chess.dao.RoomDao;
import chess.domain.board.Position;
import chess.domain.pieces.Piece;
import chess.dto.BoardSaveDto;
import chess.dto.ChessGameResponseDto;
import chess.factory.BoardFactory;
import chess.util.BoardUtil;
import java.util.List;
import java.util.Map;

public class BoardService {

    private static final int POSITION_INDEX = 0;
    private static final int PIECE_INDEX = 1;

    private final BoardDao boardDao = new BoardDao();
    private final RoomDao roomDao = new RoomDao();
    private final BoardUtil boardUtil = new BoardUtil();

    public void save(final BoardSaveDto boardSaveDto) {
        List<String> compressedBoard = boardUtil.compressBoard(boardSaveDto.getBoard());
        String compressedPosition = compressedBoard.get(POSITION_INDEX);
        String compressedPiece = compressedBoard.get(PIECE_INDEX);

        boardDao.save(boardSaveDto.getBoardId(), compressedPosition, compressedPiece, boardSaveDto.isLowerTeamTurn());
        roomDao.save(boardSaveDto.getBoardId());
    }

    public ChessGameResponseDto findBoardById(final int boardId) {
        List<String> compressedBoardFromDatabase = boardDao.findById(boardId);

        if (compressedBoardFromDatabase.isEmpty()) {
            return ChessGameResponseDto.toDto(BoardFactory.createBoard(), true);
        }

        Map<Position, Piece> unCompressedBoard = boardUtil.unCompressBoard(compressedBoardFromDatabase);

        return ChessGameResponseDto.toDto(BoardFactory.createFromUncompressedBoard(unCompressedBoard),
                boardDao.isLowerTeamTurnByBoardId(boardId));
    }

    public boolean isEmptyById(final int boardId) {
        return boardDao.findById(boardId).isEmpty();
    }

    public void delete(final int boardId) {
        boardDao.remove(boardId);
    }

    public void update(final BoardSaveDto boardSaveDto) {
        List<String> compressedBoard = boardUtil.compressBoard(boardSaveDto.getBoard());
        String compressedPosition = compressedBoard.get(POSITION_INDEX);
        String compressedPiece = compressedBoard.get(PIECE_INDEX);

        boardDao.updateByBoardId(boardSaveDto.getBoardId(), compressedPosition, compressedPiece,
                boardSaveDto.isLowerTeamTurn());
    }

    public List<Integer> findAllRoomNumbers() {
        return roomDao.findAll();
    }
}
