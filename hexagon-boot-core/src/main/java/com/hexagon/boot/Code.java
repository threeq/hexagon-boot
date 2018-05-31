package com.hexagon.boot;

public enum Code implements IEnum<Integer, String> {
    OK(200, "success"),

    USER_NOT_EXISTS(404, "user not exists"),
    USER_NOT_BUYING_GIFT_BAG(400, "user not buying gift bag"),
    USER_TICKET_ALREADY_USED(500, "user ticket already used"),

    GIFT_BAG_NOT_EXISTS(404, "gift bag not exists"),
    GIFT_BAG_NOT_EXISTS_TICKET(400, "gift bag not has ticket"),

    TICKET_NOT_EXISTS(500, "ticket not exists"),
    TICKET_VERIFY_CODE_ERROR(500, "ticket verify code error"),
    TICKET_HAD_OVERDUE(500, "ticket had overdue"),

    MOBILE_PHONE_EXIST(400, "mobile phone exist"),
    EMAIL_EXIST(400, "email esist");

    private Integer value;
    private String desc;
    Code(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer val() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }
}
