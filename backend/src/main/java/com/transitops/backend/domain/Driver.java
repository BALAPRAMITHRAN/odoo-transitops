package com.transitops.backend.domain;

public class Driver {
    private Long id;
    private User user;
    private String licenseNumber;
    private String phone;
    private Integer experienceYears;
    private String status;

    public Driver() {}

    public Driver(Long id, User user, String licenseNumber, String phone, Integer experienceYears, String status) {
        this.id = id;
        this.user = user;
        this.licenseNumber = licenseNumber;
        this.phone = phone;
        this.experienceYears = experienceYears;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
