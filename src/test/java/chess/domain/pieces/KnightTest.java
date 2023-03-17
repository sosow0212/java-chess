package chess.domain.pieces;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KnightTest {

    @ParameterizedTest(name = "Knight move success : {0} to {1}")
    @CsvSource(value = {"c3,a2", "c3,a4", "c3,b1", "c3,b5", "c3,d1", "c3,d5", "c3,e2", "c3,e4"})
    @DisplayName("Knight가 정상적인 위치로 움직인다.")
    void move_success(final String start, final String end) {
        // given
        Knight knight = new Knight(new Name("n"));

        // when  & then
        Assertions.assertDoesNotThrow(
                () -> knight.canMove(Position.from(start), Position.from(end))
        );
    }

    @ParameterizedTest(name = "Knight move fail : {0} to {1}")
    @CsvSource(value = {"c3,a1", "c3,a3", "c3,b2", "c3,b4", "c3,d2", "c3,d6", "c3,e3", "c3,e5"})
    @DisplayName("Knight가 정상적인 위치로 움직이지 않는 경우 예외를 발생시킨다.")
    void throws_exception_when_knight_moves_invalid(final String start, final String end) {
        // given
        Knight knight = new Knight(new Name("n"));

        // when  & then
        assertThatThrownBy(
                () -> knight.canMove(Position.from(start), Position.from(end))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
