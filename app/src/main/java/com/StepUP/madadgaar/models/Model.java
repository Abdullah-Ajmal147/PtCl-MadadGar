package com.StepUP.madadgaar.models;

public class Model {

    private String emp_no;
    private String name;
    private String mobile;
    private String region;
    private int tcc;
    boolean status;
    private int communication;
    private int work_place_ethics;
    private int customer_service;
    private int grooming;
    private int self_confidence;
    private int configuration_demo;
    private int copper_networks;
    private int fiber_test_equipment;
    private int copper_test_equipments;
    private int gpon_installation;
    private int optical_fiber;

    public Model() {
    }

    public Model(String emp_no, String name, String mobile, String region, int tcc, int communication, int work_place_ethics, int customer_service, int grooming, int self_confidence, int configuration_demo, int copper_networks, int fiber_test_equipment, int copper_test_equipments, int gpon_installation, int optical_fiber) {
        this.emp_no = emp_no;
        this.name = name;
        this.mobile = mobile;
        this.region = region;
        this.tcc = tcc;
        this.communication = communication;
        this.work_place_ethics = work_place_ethics;
        this.customer_service = customer_service;
        this.grooming = grooming;
        this.self_confidence = self_confidence;
        this.configuration_demo = configuration_demo;
        this.copper_networks = copper_networks;
        this.fiber_test_equipment = fiber_test_equipment;
        this.copper_test_equipments = copper_test_equipments;
        this.gpon_installation = gpon_installation;
        this.optical_fiber = optical_fiber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTcc() {
        return tcc;
    }

    public void setTcc(int tcc) {
        this.tcc = tcc;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getWork_place_ethics() {
        return work_place_ethics;
    }

    public void setWork_place_ethics(int work_place_ethics) {
        this.work_place_ethics = work_place_ethics;
    }

    public int getCustomer_service() {
        return customer_service;
    }

    public void setCustomer_service(int customer_service) {
        this.customer_service = customer_service;
    }

    public int getGrooming() {
        return grooming;
    }

    public void setGrooming(int grooming) {
        this.grooming = grooming;
    }

    public int getSelf_confidence() {
        return self_confidence;
    }

    public void setSelf_confidence(int self_confidence) {
        this.self_confidence = self_confidence;
    }

    public int getConfiguration_demo() {
        return configuration_demo;
    }

    public void setConfiguration_demo(int configuration_demo) {
        this.configuration_demo = configuration_demo;
    }

    public int getCopper_networks() {
        return copper_networks;
    }

    public void setCopper_networks(int copper_networks) {
        this.copper_networks = copper_networks;
    }

    public int getFiber_test_equipment() {
        return fiber_test_equipment;
    }

    public void setFiber_test_equipment(int fiber_test_equipment) {
        this.fiber_test_equipment = fiber_test_equipment;
    }

    public int getCopper_test_equipments() {
        return copper_test_equipments;
    }

    public void setCopper_test_equipments(int copper_test_equipments) {
        this.copper_test_equipments = copper_test_equipments;
    }

    public int getGpon_installation() {
        return gpon_installation;
    }

    public void setGpon_installation(int gpon_installation) {
        this.gpon_installation = gpon_installation;
    }

    public int getOptical_fiber() {
        return optical_fiber;
    }

    public void setOptical_fiber(int optical_fiber) {
        this.optical_fiber = optical_fiber;
    }


    public void setStatus(Object status) {
    }
}