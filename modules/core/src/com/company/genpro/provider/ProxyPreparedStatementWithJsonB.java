package com.company.genpro.provider;

import com.company.genpro.types.json.SignType;
import com.haulmont.cuba.core.sys.jdbc.ProxyPreparedStatement;
import lombok.val;
import org.postgresql.util.PGobject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class ProxyPreparedStatementWithJsonB extends ProxyPreparedStatement {

    public ProxyPreparedStatementWithJsonB(PreparedStatement statement, Connection connection) {
        super(statement, connection);
    }
    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        val customObject = SignType.DEFAULT.checkTypes(x).getValue(x);
        if (Objects.nonNull(customObject)){
            super.setObject(parameterIndex,customObject, Types.OTHER);
            return;
        }
        super.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
//        PGobject chekedObject = checkExistCustom(x);
//        if (Objects.nonNull(chekedObject)){
//            super.setObject(parameterIndex,chekedObject, Types.OTHER);
//            return;
//        }
        super.setBytes(parameterIndex, x);
    }

    private PGobject checkExistCustom(byte[] bytes) throws SQLException {
//        Object checked = convert(bytes);
        PGobject resultObject = null;
//        if (checked instanceof PgJsonB){
//            resultObject = new PGobject();
//            resultObject.setType("jsonb");
//            resultObject.setValue(((PgJsonB)checked).toString());
//        }
        return resultObject;
    }

    private Object convert(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;
        } catch (IOException | ClassNotFoundException ex) {
            // ignore close exception
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }

        }
        return null;
    }

}
