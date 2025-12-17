package model;

import java.io.Serializable;

public class SearchModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String content;

	public SearchModel() {
		super();
		this.content = null;
	}

	public SearchModel(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
