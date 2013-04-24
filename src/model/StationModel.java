package model;

// A very simple model that defines the location data that this app deals with.
public class StationModel {
    public int    idStation;
    public String StationName;
    public String PathToDoc;
    public String PathToAudio;
    public String PathToImage;
    
    public StationModel(int idStation, String StationName, String PathToDoc, String PathToAudio, String PathToImage){
     this.idStation = idStation;
     this.StationName = StationName;
     this.PathToAudio=PathToAudio;
     this.PathToDoc = PathToDoc;
     this.PathToImage = PathToImage;
    }
}
