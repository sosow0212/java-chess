package chess.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoomDaoTest {

    private final RoomDao roomDao = new RoomDao();

    @Test
    @DisplayName("DB 연결이 성공적으로 된다.")
    @Order(1)
    void connection_success() {
        // when & then
        try (final var connection = roomDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("체스 게임을 저장한다.")
    @Order(2)
    void save_room_success() {
        // when & then
        roomDao.save(1);
        roomDao.save(2);
        roomDao.save(3);
    }

    @Test
    @DisplayName("모든 방 번호를 반환한다.")
    @Order(3)
    void find_rooms_success() {
        // when
        List<Integer> roomNumbers = roomDao.findAll();

        // then
        assertAll(
                () -> assertThat(roomNumbers.contains(1)).isEqualTo(true),
                () -> assertThat(roomNumbers.contains(2)).isEqualTo(true),
                () -> assertThat(roomNumbers.contains(4)).isEqualTo(true)
        );
    }
}
