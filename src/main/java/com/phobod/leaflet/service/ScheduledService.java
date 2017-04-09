package com.phobod.leaflet.service;

import com.phobod.leaflet.model.PinColor;
import com.phobod.leaflet.model.UiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ScheduledService {
    private List<UiData> uiData;
    private Random random = new Random();
    private int maxX = 1750;
    private int maxY = 940;

    private IUiDataService uiDataService;

    @Autowired
    public ScheduledService(IUiDataService uiDataService) {
        this.uiDataService = uiDataService;
    }

    @PostConstruct
    private void init() {
        uiData = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            uiData.add(new UiData(random.nextInt(maxX), random.nextInt(maxY), "Marker " + i, getRandomPinColor()));
        }
    }

    @Scheduled(fixedRate = 5000)
    private void updateUiData(){
        for (UiData data: uiData){
            data.setX(getNewCoordinate(data.getX(), maxX));
            data.setY(getNewCoordinate(data.getY(), maxY));
        }
        uiDataService.sendUiData(uiData);
    }

    private String getRandomPinColor(){
        return PinColor.values()[random.nextInt(PinColor.values().length)].getPinName();
    }

    private int getNewCoordinate(int old, int max){
        int value = random.nextInt(200);
        if (value % 2 == 0){
            return old + value < max ? old + value : old - value;
        } else {
            return old - value > 0 ? old - value : old + value;
        }
    }
}
