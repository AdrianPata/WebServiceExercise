package ro.pata.ws.test.tomcat.db;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@javax.persistence.Entity
@javax.persistence.Table(name = "telefon", schema = "adi", catalog = "")
public class TelefonEntity {
    private int id;
    private String telefon;
    private int idnume;
    private NumeEntity owner;

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Basic
    @javax.persistence.Column(name = "telefon")
    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @ManyToOne
    @JoinColumn(name = "idnume")
    public NumeEntity getOwner() {
        return owner;
    }

    public void setOwner(NumeEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TelefonEntity that = (TelefonEntity) o;

        if (id != that.id) return false;
        if (idnume != that.idnume) return false;
        if (telefon != null ? !telefon.equals(that.telefon) : that.telefon != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (telefon != null ? telefon.hashCode() : 0);
        result = 31 * result + idnume;
        return result;
    }
}
