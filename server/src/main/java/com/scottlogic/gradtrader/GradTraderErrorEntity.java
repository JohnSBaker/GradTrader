package com.scottlogic.gradtrader;

import javax.ws.rs.core.GenericEntity;

public class GradTraderErrorEntity extends GenericEntity<GradTraderErrorMessage> {

    public GradTraderErrorEntity(String message) {
        super(new GradTraderErrorMessage(message));
    }

}
