package ro.pata.ws.test.tomcat.db;

@javax.persistence.Entity
@javax.persistence.Table(name = "prenoms", schema = "adi", catalog = "")
public class PrenomsEntity {

  private long id;
  private String prenom;
  private String genre;
  private String langage;
  private double frequence;

  @javax.persistence.Id
  @javax.persistence.Column(name = "id")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @javax.persistence.Basic
  @javax.persistence.Column(name = "prenom")
  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @javax.persistence.Basic
  @javax.persistence.Column(name = "genre")
  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  @javax.persistence.Basic
  @javax.persistence.Column(name = "langage")
  public String getLangage() {
    return langage;
  }

  public void setLangage(String langage) {
    this.langage = langage;
  }

  @javax.persistence.Basic
  @javax.persistence.Column(name = "frequence")
  public double getFrequence() {
    return frequence;
  }

  public void setFrequence(double frequence) {
    this.frequence = frequence;
  }

}
