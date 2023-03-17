package chess.domain.board;

import java.util.Objects;

public class Position {

    private static final char EMPTY_PLACE_START_INDEX = '3';
    private static final char EMPTY_PLACE_END_INDEX = '6';
    private static final char LOWER_PAWN_INDEX = '2';
    private static final char UPPER_PAWN_INDEX = '7';
    private final Row row;
    private final Col col;

    private Position(final Row row, final Col col) {
        this.row = row;
        this.col = col;
    }

    public static Position from(final String input) {
        Row row = Row.fromByInput(input.charAt(1));
        Col col = Col.fromByInput(input.charAt(0));
        return new Position(row, col);
    }

    public static Position of(final Row row, final Col col) {
        return new Position(row, col);
    }

    public boolean isPlacePositionAtFirst() {
        return EMPTY_PLACE_START_INDEX <= this.row.getIndexOfRow() && this.row.getIndexOfRow() <= EMPTY_PLACE_END_INDEX;
    }

    public boolean isLowerPawnPositionAtFirst() {
        return this.row.getIndexOfRow() == LOWER_PAWN_INDEX;
    }

    public boolean isUpperPawnPositionAtFirst() {
        return this.row.getIndexOfRow() == UPPER_PAWN_INDEX;
    }

    public char getCol() {
        return col.getIndexOfCol();
    }

    public char getRow() {
        return row.getIndexOfRow();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
