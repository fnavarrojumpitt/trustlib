package com.jumpitt.trustlib.network;

import com.jumpitt.trustlib.model.Device;
import com.jumpitt.trustlib.model.SIM;

import java.util.List;

public class TrifleBody {
    private Device device;
    private List<SIM> sim;

    public TrifleBody() {
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<SIM> getSim() {
        return sim;
    }

    public void setSim(List<SIM> sim) {
        this.sim = sim;
    }
}
