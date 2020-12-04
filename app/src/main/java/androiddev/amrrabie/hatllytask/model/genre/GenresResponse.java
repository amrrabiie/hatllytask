package androiddev.amrrabie.hatllytask.model.genre;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenresResponse{

	@SerializedName("genres")
	private List<GenresItem> genres;

	public void setGenres(List<GenresItem> genres){
		this.genres = genres;
	}

	public List<GenresItem> getGenres(){
		return genres;
	}

	@Override
 	public String toString(){
		return 
			"GenresResponse{" + 
			"genres = '" + genres + '\'' + 
			"}";
		}
}