package com.jumpitt.trustlib.model;

import com.google.gson.annotations.SerializedName;

public class Device {
    @SerializedName("imei")
    private String imei;
    @SerializedName("board")
    private String board;
    @SerializedName("brand")
    private String brand;
    @SerializedName("device")
    private String device;
    @SerializedName("display")
    private String display;
    @SerializedName("fingerprint")
    private String fingerprint;
    @SerializedName("hardware")
    private String hardware;
    @SerializedName("host")
    private String host;
    @SerializedName("id")
    private String id;
    @SerializedName("manufacturer")
    private String manufacturer;
    @SerializedName("model")
    private String model;
    @SerializedName("product")
    private String product;
    @SerializedName("serial")
    private String serial;
    @SerializedName("processor_quantity")
    private String processorQuantity;
    @SerializedName("processor_model_name")
    private String processorModelName;
    @SerializedName("processor_bogomips")
    private String processorBogomips;
    @SerializedName("processor_features")
    private String processorFeatures;
    @SerializedName("processor_hardware")
    private String processorHardware;
    @SerializedName("processor_revision")
    private String processorRevision;
    @SerializedName("processor_serial")
    private String processorSerial;
    @SerializedName("processor_device")
    private String processorDevice;
    @SerializedName("processor_radio")
    private String processorRadio;
    @SerializedName("processor_msm_hardware")
    private String processorMsmHardware;
    @SerializedName("cpu_implementer")
    private String cpuImplementer;
    @SerializedName("cpu_variant")
    private String cpuVariant;
    @SerializedName("cpu_architecture")
    private String cpuArchitecture;
    @SerializedName("cpu_revision")
    private String cpuRevision;
    @SerializedName("cpu_part")
    private String cpuPart;
    @SerializedName("kernel_stack")
    private String kernelStack;
    @SerializedName("software_version")
    private String softwareVersion;
    @SerializedName("mem_total")
    private String memTotal;
    @SerializedName("swap_total")
    private String swapTotal;
    @SerializedName("wlan0_mac")
    private String wlan0Mac;
    private String UUID;

    public Device() {
    }

    public String getDisplay() {
        return display;
    }

    public String getWlan0Mac() {
        return wlan0Mac;
    }

    public void setWlan0Mac(String wlan0Mac) {
        this.wlan0Mac = wlan0Mac;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getProcessorQuantity() {
        return processorQuantity;
    }

    public void setProcessorQuantity(String processorQuantity) {
        this.processorQuantity = processorQuantity;
    }

    public String getProcessorModelName() {
        return processorModelName;
    }

    public void setProcessorModelName(String processorModelName) {
        this.processorModelName = processorModelName;
    }

    public String getProcessorBogomips() {
        return processorBogomips;
    }

    public void setProcessorBogomips(String processorBogomips) {
        this.processorBogomips = processorBogomips;
    }

    public String getProcessorFeatures() {
        return processorFeatures;
    }

    public void setProcessorFeatures(String processorFeatures) {
        this.processorFeatures = processorFeatures;
    }

    public String getProcessorHardware() {
        return processorHardware;
    }

    public void setProcessorHardware(String processorHardware) {
        this.processorHardware = processorHardware;
    }

    public String getProcessorRevision() {
        return processorRevision;
    }

    public void setProcessorRevision(String processorRevision) {
        this.processorRevision = processorRevision;
    }

    public String getProcessorSerial() {
        return processorSerial;
    }

    public void setProcessorSerial(String processorSerial) {
        this.processorSerial = processorSerial;
    }

    public String getProcessorDevice() {
        return processorDevice;
    }

    public void setProcessorDevice(String processorDevice) {
        this.processorDevice = processorDevice;
    }

    public String getProcessorRadio() {
        return processorRadio;
    }

    public void setProcessorRadio(String processorRadio) {
        this.processorRadio = processorRadio;
    }

    public String getProcessorMsmHardware() {
        return processorMsmHardware;
    }

    public void setProcessorMsmHardware(String processorMsmHardware) {
        this.processorMsmHardware = processorMsmHardware;
    }

    public String getCpuImplementer() {
        return cpuImplementer;
    }

    public void setCpuImplementer(String cpuImplementer) {
        this.cpuImplementer = cpuImplementer;
    }

    public String getCpuVariant() {
        return cpuVariant;
    }

    public void setCpuVariant(String cpuVariant) {
        this.cpuVariant = cpuVariant;
    }

    public String getCpuArchitecture() {
        return cpuArchitecture;
    }

    public void setCpuArchitecture(String cpuArchitecture) {
        this.cpuArchitecture = cpuArchitecture;
    }

    public String getCpuRevision() {
        return cpuRevision;
    }

    public void setCpuRevision(String cpuRevision) {
        this.cpuRevision = cpuRevision;
    }

    public String getCpuPart() {
        return cpuPart;
    }

    public void setCpuPart(String cpuPart) {
        this.cpuPart = cpuPart;
    }

    public String getKernelStack() {
        return kernelStack;
    }

    public void setKernelStack(String kernelStack) {
        this.kernelStack = kernelStack;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }

    public String getSwapTotal() {
        return swapTotal;
    }

    public void setSwapTotal(String swapTotal) {
        this.swapTotal = swapTotal;
    }


    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
