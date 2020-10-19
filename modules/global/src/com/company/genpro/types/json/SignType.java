package com.company.genpro.types.json;

import com.google.gson.Gson;
import lombok.val;
import org.postgresql.util.PGobject;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum SignType {

    SIGN_TYPE_JSON {
        @Override
        public Object convert(Object value) {
            val template = GSON.fromJson((String) value, LinkedHashMap.class);
            template.put(TYPE, STRING_JSON_TYPE);
            return GSON.toJson(template);
        }

        @Override
        public Object getType() {
            return STRING_JSON_TYPE;
        }

        @Override
        public Boolean checkType(final Object value) {
            return ((String) value).contains(SIGN_STRING_JSON_TYPE);
        }

        @Override
        public PGobject getValue(Object value) throws SQLException {
            PGobject o = new PGobject();
            o.setType(STRING_JSON_TYPE);
            o.setValue((String) value);
            return o;
        }
    }, DEFAULT {
        @Override
        public Object convert(Object value) {
            throw new RuntimeException("This is not implement here");
        }

        @Override
        public Object getType() {
            throw new RuntimeException("This is not implement here");
        }

        @Override
        public Boolean checkType(Object value) {
            throw new RuntimeException("This is not implement here");
        }

        @Override
        public Object getValue(Object value) {
            return null;
        }
    };

    public final String TYPE = "type";

    public final String STRING_JSON_TYPE = "jsonb";
    public final String SIGN_STRING_JSON_TYPE = String.format("\"%s\":\"%s\"", TYPE, STRING_JSON_TYPE);

    final Gson GSON = GsonBuilderUtils.gsonBuilderWithBase64EncodedByteArrays().create();

    abstract public Object convert(final Object value);

    abstract public Object getType();

    abstract public Boolean checkType(final Object value);

    abstract public Object getValue(final Object value) throws SQLException;

    public SignType checkTypes(final Object value) {
        return Arrays.stream(values())
                .filter(f -> !Objects.equals(f, SignType.DEFAULT) && f.checkType(value))
                .findFirst().orElse(SignType.DEFAULT);
    }

}
