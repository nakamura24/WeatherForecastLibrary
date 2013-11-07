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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class AndroidSaxFeedParser {
	private static final String TAG = "AndroidSaxFeedParser";

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final String TITLE = "title";
	static final String ITEM = "item";
	static final String DESCRIPTION = "description";

	private final URL feedUrl;
	static final String RSS = "rss";

	public AndroidSaxFeedParser(String feedUrl) {
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Message> parse() {
		final Message currentMessage = new Message();
		RootElement root = new RootElement(RSS);
		final ArrayList<Message> messages = new ArrayList<Message>();
		Element channel = root.getChild(CHANNEL);
		Element item = channel.getChild(ITEM);
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				messages.add(currentMessage.copy());
			}
		});
		item.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						Log.d(TAG, "TITLE - " + body);
						currentMessage.setTitle(body);
					}
				});
		item.getChild(PUB_DATE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						Log.d(TAG, "PUB_DATE - " + body);
						currentMessage.setDate(body);
					}
				});
		item.getChild(DESCRIPTION).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						Log.d(TAG, "DESCRIPTION - " + body);
						currentMessage.setDescription(body);
					}
				});
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return messages;
	}
}
