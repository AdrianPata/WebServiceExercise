package ro.pata.ws.test.tomcat.db;


import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.Set;

@javax.persistence.Entity
@javax.persistence.Table(name = "nume", schema = "adi", catalog = "")
public class NumeEntity {
    private int id;
    private String nume;
    private Set<TelefonEntity> agenda;

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
    @javax.persistence.Column(name = "nume")
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @OneToMany(mappedBy = "owner", cascade= CascadeType.ALL)
    public Set<TelefonEntity> getAgenda() {
        return agenda;
    }

    public void setAgenda(Set<TelefonEntity> agenda) {
        this.agenda = agenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumeEntity that = (NumeEntity) o;

        if (id != that.id) return false;
        if (nume != null ? !nume.equals(that.nume) : that.nume != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nume != null ? nume.hashCode() : 0);
        return result;
    }
}
