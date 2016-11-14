package com.barakiha.chineseeasy1.Util;

import com.barakiha.chineseeasy1.Model.CharLesson;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanaskuliah on 10/11/2016.
 */

public class XMLPullParserHandler {
    List<CharLesson> chinesechars;
    private CharLesson chinesechar;
    private String text;

    public XMLPullParserHandler() {
        chinesechars = new ArrayList<CharLesson>();
    }

    public List<CharLesson> getChinesechars() {
        return chinesechars;
    }

    public List<CharLesson> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("chinesechar")) {
                            // create a new instance of employee
                            chinesechar = new CharLesson();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("chinesechar")) {
                            // add employee object to list
                            chinesechars.add(chinesechar);
                        } else if (tagname.equalsIgnoreCase("tone")) {
                            chinesechar.setTone(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("character")) {
                            chinesechar.setCharacter(text);
                        } else if (tagname.equalsIgnoreCase("pinyin")) {
                            chinesechar.setPinyin(text);
                        } else if (tagname.equalsIgnoreCase("mean")) {
                            chinesechar.setMean(text);
                        } else if (tagname.equalsIgnoreCase("clue")) {
                            chinesechar.setClue(text);
                        } else if (tagname.equalsIgnoreCase("use")) {
                            chinesechar.setUse(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return chinesechars;
    }
}