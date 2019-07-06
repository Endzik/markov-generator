package com.github.endzik.markov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class WebPageWordsSource extends StringWordsSource {

    public WebPageWordsSource(String source) throws IOException {
        super(readFromWebsite(source));
    }

    private static String readFromWebsite(String url) throws IOException {
        List<String> paragraphs = getParagraphs(url);
        return String.join("\t", paragraphs);
    }

    private static List<String> getParagraphs(String url) throws IOException {
        System.out.println("Reading paragraphs from: " + url);
        Document document = Jsoup.connect(url).get();
        return document.select("p").stream().map(Element::text).collect(toList());
    }
}
