package com.pas.utils;

public class exceptionMessages {
    public enum exMsg {
        LOGIN_TAKEN("Login already taken"),
        ENTITY_NOT_FOUND_MESSAGE("Entity with given ID doesn't exist"),

        ORDER_CUSTOMER_SUSPENDED("Can't create order, customer suspended"),
        ORDER_ITEM_OUT_OF_STOCK("Can't create order, items out of stock"),
        ORDER_INSUFFICIENT_FUNDS("Can't create order, user doesn't have enough money"),
        ORDER_DELETE_ONGOING_ERROR("Cant delete ongoing order"),
        ORDER_VIOLATED_BUSINESS_LOGIC("Couldn't create order, violated business logic"),

        PRODUCT_IN_UNFINISHED_ORDER("Cant delete item present in unfinished order")

        ;

        private final String text;

        exMsg(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}


