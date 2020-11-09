package com.scio.quantum.ai.pii.models.file;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.scio.quantum.ai.pii.models.geocoordinates.GeoCoordinate;
import com.scio.quantum.ai.pii.models.namedentity.NamedEntity;

import java.util.ArrayList;
import java.util.Objects;

public class CandidateFile {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("filetype")
    @Expose
    private String filetype;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("namedentity")
    @Expose
    private ArrayList<NamedEntity> namedEntities = null;
    @SerializedName("geocoordinates")
    @Expose
    private ArrayList<GeoCoordinate> geoCoordinates = null;

    public CandidateFile(String path, String filetype, String content, String filename) {
        this.path = path;
        this.filetype = filetype;
        this.content = content;
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ArrayList<NamedEntity> getNamedEntities() {
        return namedEntities;
    }

    public void setNamedEntities(ArrayList<NamedEntity> namedEntities) {
        this.namedEntities = namedEntities;
    }

    public ArrayList<GeoCoordinate> getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(ArrayList<GeoCoordinate> geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    @Override
    public String toString() {
        return "CandidateFile{" +
                "path='" + path + '\'' +
                ", filetype='" + filetype + '\'' +
                ", content='" + content + '\'' +
                ", filename='" + filename + '\'' +
                ", namedEntities=" + namedEntities +
                ", geoCoordinates=" + geoCoordinates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateFile that = (CandidateFile) o;
        return Objects.equals(getPath(), that.getPath()) &&
                Objects.equals(getFiletype(), that.getFiletype()) &&
                Objects.equals(getFilename(), that.getFilename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getFiletype(), getFilename());
    }
}
