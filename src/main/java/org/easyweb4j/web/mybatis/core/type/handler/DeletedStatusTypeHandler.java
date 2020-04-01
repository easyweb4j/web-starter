package org.easyweb4j.web.mybatis.core.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.easyweb4j.web.core.dao.DeletedStatus;
import org.easyweb4j.web.core.exception.SystemInternalException;

/**
 * <p>转化{@see org.easyweb4j.web.core.dao.DeletedStatus}和JDBC的datetime或整形值，实现伪删除,抽象类</p>
 *
 * @author Ray(chenlei linxray @ gmail.com)
 * @date 2020/03/31
 * @since 1.0
 */
public class DeletedStatusTypeHandler extends BaseTypeHandler<DeletedStatus> {

  public static final Timestamp NORMAL_DATE = Timestamp.valueOf(
    LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0)
  );

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, DeletedStatus parameter,
    JdbcType jdbcType) throws SQLException {
    if (null == jdbcType) {
      throw new SystemInternalException(
        "JdbcType(TIMESTAMP|BIT|INTEGER) must be supplied for DeletedStatus");
    }
    switch (jdbcType) {
      case BIT:
        ps.setByte(i, (byte) parameter.ordinal());
        break;

      case INTEGER:
        ps.setInt(i, parameter.ordinal());
        break;

      case TIMESTAMP:
        switch (parameter) {
          case NORMAL:
            ps.setTimestamp(i, NORMAL_DATE);
            break;
          default:
            ps.setTimestamp(i, Timestamp.valueOf(LocalDateTime.now()));
            break;
        }
        break;

      default:
        throw new SystemInternalException(
          "JdbcType not implemented for DeletedStatus: " + jdbcType.toString());
    }
  }

  @Override
  public DeletedStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
    ResultSetMetaData metaData = rs.getMetaData();
    int foundColumnNameInx = -1;
    for (int i = 1; i <= metaData.getColumnCount(); i++) {
      if (metaData.getColumnName(i).equals(columnName)) {
        foundColumnNameInx = i;
        break;
      }
    }

    if (0 > foundColumnNameInx) {
      throw new SystemInternalException(
        "DeletedStatusTypeHandler columneName font found: " + columnName);
    }

    return getNullableResult(metaData.getColumnType(foundColumnNameInx), rs, foundColumnNameInx);
  }

  private DeletedStatus getNullableResult(int columnType, ResultSet rs, int columnIndex)
    throws SQLException {
    switch (columnType) {
      case Types.INTEGER:
        int intVal = rs.getInt(columnIndex);
        return convert2DeletedStatus4ByOrdinal(intVal);

      case Types.TIMESTAMP:
        return convert2DeletedStatus4TS(rs.getTimestamp(columnIndex));

      default:
        byte byteVal = rs.getByte(columnIndex);
        return convert2DeletedStatus4ByOrdinal(byteVal);

    }
  }

  private DeletedStatus convert2DeletedStatus4ByOrdinal(int intVal) {
    if (intVal > -1 && intVal < DeletedStatus.values().length) {
      return DeletedStatus.values()[intVal];
    }
    return null;
  }

  @Override
  public DeletedStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return getNullableResult(rs.getMetaData().getColumnType(columnIndex), rs, columnIndex);
  }

  private DeletedStatus convert2DeletedStatus4TS(Timestamp timestamp) {
    if (null == timestamp) {
      return null;
    }
    return NORMAL_DATE.equals(timestamp) ? DeletedStatus.NORMAL : DeletedStatus.DELETED;
  }

  @Override
  public DeletedStatus getNullableResult(CallableStatement cs, int columnIndex)
    throws SQLException {
    ResultSetMetaData metaData = cs.getMetaData();
    switch (metaData.getColumnType(columnIndex)) {
      case Types.INTEGER:
        int intVal = cs.getInt(columnIndex);
        return convert2DeletedStatus4ByOrdinal(intVal);

      case Types.TIMESTAMP:
        return convert2DeletedStatus4TS(cs.getTimestamp(columnIndex));

      default:
        byte byteVal = cs.getByte(columnIndex);
        return convert2DeletedStatus4ByOrdinal(byteVal);

    }
  }
}

