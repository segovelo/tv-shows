package tv.shows.app.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.net.*;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import tv.shows.app.httpclient.HttpClientUtility;
import tv.shows.app.beans.TVShows;

/** 
* 20 Feb 2024 23:23:11
* @Javadoc TODO 
*
* @author Segovelo  **/

public class TVShowsController {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
       // BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String genre = bufferedReader.readLine();

//        Console console = System.console();
//
//        if (console == null) {
//            System.out.print("No console available");
//            return;
//        }

        //String genre = console.readLine("Enter your favourite genre: ");
        String result = Result.bestInGenre(genre);
//        console.printf(result);

        System.out.println(result);
        bufferedReader.close();
        //bufferedWriter.close();
    }
}

class Result {

    /*
     * Complete the 'bestInGenre' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING genre as parameter.
     *
     * Base URL: https://jsonmock.hackerrank.com/api/tvseries?page=
     * i.e.: https://jsonmock.hackerrank.com/api/tvseries?page=1
     */

    public static String bestInGenre(String genre) {
        // Write your code here
    	     List<String> urls = new ArrayList<String>(Collections.nCopies(20, "https://jsonmock.hackerrank.com/api/tvseries"));
    	     String url = "https://jsonmock.hackerrank.com/api/tvseries";
		    //List<TVShows> tvShows = HttpClientUtility.retrieveTVShows(url,"1"); //("http://localhost:8085/api/tvseries", "1");
    	     List<TVShows> tvShows = HttpClientUtility.retrieveTVShowsAsync(urls);
    	     Optional <TVShows> show = tvShows.stream()
            		             .filter(tvShow -> tvShow.getGenre().toLowerCase().contains(genre.toLowerCase()))
            		             .max(TVShows::compareTo);//.max(Comparator.comparing(TVShows::getImdb_rating))
            		             //.orElseThrow(NoSuchElementException::new);
    	     List<TVShows> tvShowsByGenre = tvShows.stream()
		             .filter(tvShow -> tvShow.getGenre().toLowerCase().contains(genre.toLowerCase()))
		             .collect(Collectors.toList());
    	for(TVShows tvShow : tvShowsByGenre) {
			System.out.println(String.format("Name: %s - Rating: %.1f - Genre: %s", tvShow.getName(),tvShow.getImdb_rating(), tvShow.getGenre()));
		}
    	if(show.isPresent()) {
          return show.get().toString();
    	}
    	else return String.format("There is no show in %s genre.",genre);
    }
}
        
