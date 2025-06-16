package br.edu.ifgoiano.Empreventos.dto.request;



import java.io.Serializable;

public class ListenerRequestDTO implements Serializable {

    private String company;
    private String position;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
