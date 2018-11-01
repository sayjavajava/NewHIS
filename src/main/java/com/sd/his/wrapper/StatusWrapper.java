package com.sd.his.wrapper;

public class StatusWrapper {
    private Long id;
    private String   name;
    private String abbreveation;
    private boolean active;
    private String colorHash;

    public Status() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreveation() {
        return abbreveation;
    }

    public void setAbbreveation(String abbreveation) {
        this.abbreveation = abbreveation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getColorHash() {
        return colorHash;
    }

    public void setColorHash(String colorHash) {
        this.colorHash = colorHash;
    }
}
