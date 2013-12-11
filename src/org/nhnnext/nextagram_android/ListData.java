package org.nhnnext.nextagram_android;

public class ListData {
	private String owner;
	private String contents;
	private String imgName;
	private int commentNum;

	public ListData(String owner, String contents, String imgName,
			int commentNum) {
		this.owner = owner;
		this.contents = contents;
		this.imgName = imgName;
		this.commentNum = commentNum;
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

	public int getCommentNum() {
		return commentNum;
	}

}
