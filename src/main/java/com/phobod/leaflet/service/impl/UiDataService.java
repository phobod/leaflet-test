package com.phobod.leaflet.service.impl;

import com.phobod.leaflet.model.UiData;
import com.phobod.leaflet.service.IUiDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UiDataService implements IUiDataService{

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public UiDataService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendUiData(List<UiData> uiData) {
        simpMessagingTemplate.convertAndSend("/topic/data", uiData);
    }
}
