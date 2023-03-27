package chess.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {

    public void save(final int boardId) {
        final String query = "INSERT INTO room (board_id) VALUES (?)";

        try (final PreparedStatement preparedStatement = MySqlManager.getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, boardId);

            preparedStatement.addBatch();
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("room 저장 실패");
        }
    }

    public List<Integer> findAll() {
        final String query = "SELECT room_id FROM room;";

        List<Integer> roomNumber = new ArrayList<>();

        try (final var connection = MySqlManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                roomNumber.add(result.getInt("room_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomNumber;
    }
}
