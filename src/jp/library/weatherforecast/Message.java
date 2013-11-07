/*
 * Copyright (C) 2013 M.Nakamura
 *
 * This software is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 2.1 Japan License.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://creativecommons.org/licenses/by-nc-sa/2.1/jp/legalcode
 */
package jp.library.weatherforecast;

public class Message implements Comparable<Message> {
	private String title;
	private String date;
	private String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date.trim();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description.trim();
	}

	public Message copy() {
		Message copy = new Message();
		copy.title = title;
		copy.date = date;
		copy.description = description;
		return copy;
	}

	public int compareTo(Message another) {
		if (another == null)
			return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}
}
