package tv.shows.app.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** 
* 21 Feb 2024 00:58:57
* @Javadoc TODO 
*
* @author Segovelo  **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TVShowsResponse {
	
	@JsonProperty("page")
	private Long page;
	
	@JsonProperty("per_page")
	private  Integer per_page;
	
	@JsonProperty("total")
	private Long total;
	
	@JsonProperty("total_pages")
	private Long total_pages;
	
	@JsonProperty("data")
	private List<TVShows> data;

	public Long getPage() {
		return page;
	}

	public Integer getPer_page() {
		return per_page;
	}

	public Long getTotal() {
		return total;
	}

	public Long getTotal_pages() {
		return total_pages;
	}

	public List<TVShows> getData() {
		return data;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setTotal_pages(Long total_pages) {
		this.total_pages = total_pages;
	}

	public void setData(List<TVShows> data) {
		this.data = data;
	}	
}
