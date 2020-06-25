package com.tt.work.starter.impoetfile.enums;

/**
 * @author wenshilong
 * @date 2020/06/23
 */
public enum ExplainStateEnum {

    /**
     * 未接
     */
    MISSED_CALL("0"),

    /**
     * 同意
     */
    AGREE("1"),

    /**
     * 无效
     */
    invalid("2"),

    /**
     * 不同意
     */
    UNAGREE("3"),
    ;

    /**
     * The Value.
     */
    private String value;

    /**
     * Instantiates a new Validity reminder enum.
     *
     * @param value the value
     * @date
     */
    ExplainStateEnum(String value) {
        this.value = value;
    }

    /**
     * 获取该枚举的值
     *
     * @return
     */
    public String getValue() {
        return value;
    }
}

