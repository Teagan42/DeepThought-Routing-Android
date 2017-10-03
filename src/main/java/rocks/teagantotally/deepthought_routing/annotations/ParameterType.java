package rocks.teagantotally.deepthought_routing.annotations;

/**
 * Created by tglenn on 9/30/17.
 */

public enum ParameterType {
    STRING(String.class),
    SHORT(Short.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BOOLEAN(Boolean.class),
    BYTE(Byte.class);

    private Class typeClass;

    ParameterType(Class typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * @return The class simple name for this type
     */
    public String getTypeName() {
        return typeClass.getSimpleName();
    }
}
