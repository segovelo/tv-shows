package tv.shows.app.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
/** 
* 20 Feb 2024 23:38:52
* @Javadoc TODO 
*
* @author Segovelo  **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TVShows implements Comparable<TVShows>{
	@JsonProperty("name")
    private String name;
	
	@JsonProperty("runtime_of_series")
    private String runtime_of_series;
	
	@JsonProperty("certificate")
    private String certificate;
	
	@JsonProperty("runtime_of_episodes")
    private String runtime_of_episodes;
	
	@JsonProperty("genre")
	private String genre;
	
	@JsonProperty("imdb_rating")
    private Float imdb_rating;
	
	@JsonProperty("overview")
    private String overview;
	
	@JsonProperty("no_of_votes")
    private Long no_of_votes;
	
	@JsonProperty("id")
    private Long id;
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getOverview() {
		return overview;
	}
	public Long getNo_of_votes() {
		return no_of_votes;
	}
	public Long getId() {
		return id;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public void setNo_of_votes(Long no_of_votes) {
		this.no_of_votes = no_of_votes;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getImdb_rating() {
		return imdb_rating;
	}
	public void setImdb_rating(Float imdb_rating) {
		this.imdb_rating = imdb_rating;
	}
	public String getName() {
		return name;
	}
	public String getRuntime_of_series() {
		return runtime_of_series;
	}
	public String getCertificate() {
		return certificate;
	}
	public String getRuntime_of_episodes() {
		return runtime_of_episodes;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRuntime_of_series(String runtime_of_series) {
		this.runtime_of_series = runtime_of_series;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public void setRuntime_of_episodes(String runtime_of_episodes) {
		this.runtime_of_episodes = runtime_of_episodes;
	}
	@Override
	public String toString() {
		return "TVShows [name=" + name + ", runtime_of_series=" + runtime_of_series + ", certificate=" + certificate
				+ ", runtime_of_episodes=" + runtime_of_episodes + ", genre=" + genre + ", imdb_rating=" + imdb_rating
				+ ", overview=" + overview + ", no_of_votes=" + no_of_votes + ", id=" + id + "]";
	}

	@Override
	public int compareTo(TVShows other) {
		int result = Float.compare(this.imdb_rating, other.getImdb_rating());
		if (result == 0) {
			return other.getName().compareToIgnoreCase(this.name);
		}
		return result;
	}
   
}
