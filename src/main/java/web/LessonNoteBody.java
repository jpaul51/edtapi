package web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LessonNoteBody {
	@JsonProperty(value = "title")
	public String title;
	
	@JsonProperty(value = "description")
	public String description;
	
	@JsonProperty(value = "importance")
	public String importance;
}
