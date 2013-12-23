package org.nhnnext.nextagram_android;

public class ListData {
	private int id;
	private String owner;
	private String contents;
	private String imgName;

	public ListData(int id, String owner, String contents, String imgName) {
		this.id = id;
		this.owner = owner;
		this.contents = contents;
		this.imgName = imgName;
	}

	public int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public String getContents() {
		return contents;
	}

	public String getImgName() {
		return imgName;
	}

}
