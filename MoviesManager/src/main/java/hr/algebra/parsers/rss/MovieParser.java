/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parsers.rss;

import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author s_vre
 */
public class MovieParser {

    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String ATTRIBUTE_URL = "src";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL); //Otvara konekciju

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE:
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case PUBLISHED_DATE:
                                    if (!data.isEmpty()) {
                                        try {
                                            LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                            movie.setPublishedDate(publishedDate);
                                        } catch (DateTimeParseException e) {
                                            System.err.println("Failed to parse date: " + data);
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (!data.isEmpty()) {
                                        String descriptionText = extractDescription(data);
                                        if (descriptionText != null && !descriptionText.isEmpty()) {
                                            movie.setDescription(descriptionText);
                                        }
                                    }
                                    break;
                                case PICTURE:
                                    if (!data.isEmpty() && movie.getPicturePath() == null) {
                                        movie.setPicturePath(data);
                                        handlePicture(movie, data);
                                    }
                                    break;
                                case LINK:
                                    if (!data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                    break;
                                case GENRE:
                                    if (!data.isEmpty()) {
                                        String lineGenres = data;
                                        String[] genreNames = lineGenres.split(",");
                                        Set<Genre> genreSet = new HashSet<>();
                                        for (String genreName : genreNames) {
                                            genreSet.add(new Genre(genreName.trim()));
                                        }
                                        movie.setGenres(genreSet);
                                    }
                                    break;
                                case ACTOR:
                                    if (!data.isEmpty()) {
                                        String lineActor = data;
                                        String[] actorNames = lineActor.split(",");
                                        Set<Person> actorSet = new HashSet<>();
                                        for (String actorName : actorNames) {
                                            actorSet.add(new Person(actorName.trim()));
                                        }
                                        movie.setActors(actorSet);
                                    }
                                    break;
                                case DIRECTOR:
                                    if (!data.isEmpty()) {
                                        String lineDirector = data;
                                        String[] directorNames = lineDirector.split(",");
                                        Set<Person> directorSet = new HashSet<>();
                                        for (String directorName : directorNames) {
                                            directorSet.add(new Person(directorName.trim()));
                                        }
                                        movie.setDirectors(directorSet);
                                    }
                                    break;
                                default:
                                    throw new AssertionError(tagType.get().name());
                            }
                        }
                    }
                }
            }
        }
        return movies;
    }

    private static String extractDescription(String data) {
        String imgEndTag = ">";
        String divStartTag = "<div";
        String divEndTag = "</div>";
        String brLinkTag = "<br /><a href";

        int startIndex = data.indexOf(imgEndTag) + 1; // Početni indeks će biti nakon '>' od <img ...> tag-a
        int endIndex;

        if (data.substring(startIndex).startsWith(divStartTag)) {
            // Ako je sljedeći div tag, pronađi kraj div-a i postavi ga za endIndex
            endIndex = data.indexOf(divEndTag, startIndex);
            if (endIndex != -1) {
                endIndex += divEndTag.length(); // Uključi i </div>
            }
        } else {
            // Ako sljedeći nije div tag, pronađi početak od <br /><a href...
            endIndex = data.indexOf(brLinkTag, startIndex);
        }

        if (startIndex != -1 && endIndex != -1) {
            String extracted = data.substring(startIndex, endIndex).trim(); //Ako su početni i završni indeksi pronađeni, vrati izvučeni string
            extracted = extracted.replaceAll("<div.*?>", "").replaceAll("<br/>", " "); //Izbaci div i br tagove
            return extracted;
        }
        return "";
    }

    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {
        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String name = UUID.randomUUID() + ext;
        String localPath = DIR + File.separator + name;

        FileUtils.copyFromUrl(pictureUrl, localPath);

        movie.setPicturePath(localPath);
    }

    private MovieParser() {
    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUBLISHED_DATE("pubDate"),
        DESCRIPTION("description"),
        PICTURE("plakat"),
        LINK("link"),
        GENRE("zanr"),
        ACTOR("glumci"),
        DIRECTOR("redatelj");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        public static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
