package com.ruben.clientandroid.Api.eventbus;

import com.ruben.clientandroid.Models.Event;

public interface EventBusCallback {
    void OnEvent(Event event);
}
