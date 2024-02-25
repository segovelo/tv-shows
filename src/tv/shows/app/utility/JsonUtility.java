package tv.shows.app.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tv.shows.app.beans.TVShows;
import tv.shows.app.beans.TVShowsResponse;

/** 
* 20 Feb 2024 23:36:43
* @Javadoc TODO 
*
* @author Segovelo  **/

public class JsonUtility {

	
    public static TVShows jsonToTVShow(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        TVShows tvShows = null;
        try {
            tvShows = mapper.readValue(jsonString, new TypeReference<TVShows>() {
            });
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }    
        return tvShows;
    }
    
    public static List<TVShows> jsonToTVShows(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        List<TVShows> tvShows = new ArrayList<>();
        try {
        	TVShowsResponse tvShowsResponse = mapper.readValue(jsonString, new TypeReference<TVShowsResponse>() {});
            tvShows = tvShowsResponse.getData();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }      
        return tvShows;
    }
}
