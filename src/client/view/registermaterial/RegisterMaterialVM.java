package client.view.registermaterial;

import client.core.ModelFactoryClient;
import client.model.material.Place;
import client.model.material.strategy.MaterialCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegisterMaterialVM
{
  private ObservableList<String> materialType;
  public RegisterMaterialVM()
  {
    materialType = FXCollections.observableArrayList();
    materialType.add("Book");
    materialType.add("EBook");
    materialType.add("AudioBook");
    materialType.add("CD");
    materialType.add("DVD");
  }

  public ObservableList<String> getMaterialType()
  {
    return materialType;
  }

  public void addBook(String title, String publisher, String releaseDate,
      String description, String tags, String targetAudience, String language,
      String isbn, int pageCount, Place place, MaterialCreator author, String genre,
      String url)
  {
    ModelFactoryClient.getInstance().getMaterialModelClient()
        .registerBook(title, publisher, releaseDate, description, tags,
            targetAudience, language, isbn, pageCount, place, author, genre,
            url);
  }

  public void addEBook(String title, String publisher, String releaseDate,
      String description, String tags, String targetAudience, String language,
      String isbn, int pageCount, String licenseNr, MaterialCreator author, String genre,
      String url)
  {
    ModelFactoryClient.getInstance().getMaterialModelClient()
        .registerEBook(title, publisher, releaseDate, description, tags,
            targetAudience, language, isbn, pageCount, licenseNr, author,
            genre, url);
  }

  public void addAudioBook(String title, String publisher, String releaseDate,
      String description, String tags, String targetAudience, String language,
      int playDuration, String genre, MaterialCreator author, String url)
  {
    ModelFactoryClient.getInstance().getMaterialModelClient()
        .registerAudioBook(title, publisher, releaseDate, description, tags,
            targetAudience, language, playDuration, genre, author, url);
  }

  public void addDVD(String title, String publisher, String releaseDate,
      String description, String tags, String targetAudience, String language,
      String subtitlesLanguage, int playDuration, Place placeID, String genre,
      String url)
  {
    ModelFactoryClient.getInstance().getMaterialModelClient()
        .registerDVD(title, publisher, releaseDate, description, tags,
            targetAudience, language, subtitlesLanguage, playDuration, placeID,
            genre, url);
  }

  public void addCD(String title, String publisher, String releaseDate,
      String description, String tags, String targetAudience, String language,
      int playDuration, Place place, String genre, String url)
  {
    ModelFactoryClient.getInstance().getMaterialModelClient()
        .registerCD(title, publisher, releaseDate, description, tags,
            targetAudience, language, playDuration, place, genre, url);
  }
}
