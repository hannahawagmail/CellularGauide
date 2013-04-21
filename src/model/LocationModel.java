package model;

// A very simple model that defines the location data that this app deals with.
public class LocationModel {
    public int    idPlace;
    public String placeName;
    public int    idArea;
    public String areaName;
    public String travelTime;
    public String wazeLink;
    public Double attitude;
    public Double latitude;
    public String discribtion;
    
    public LocationModel(int    idPlace, String placeName, int    idArea, String areaName, String travelTime, String wazeLink, Double attitude, Double latitude, String discribtion) {
        this.idPlace      = idPlace;
        this.placeName = placeName;
        this.idArea = idArea;
        this.areaName = areaName;
        this.travelTime = travelTime;
        this.wazeLink = wazeLink;
        this.attitude = attitude;
        this.latitude = latitude;
        this.discribtion = discribtion;
    }
}
