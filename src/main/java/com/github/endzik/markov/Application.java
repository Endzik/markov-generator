package com.github.endzik.markov;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Application {

    private static final List<String> SOURCE_URLS = Arrays.asList(
            "http://e-opowiadania.pl/opowiadania/kryminal/122-historia-mercedes",
            "http://e-opowiadania.pl/opowiadania/kryminal/122-historia-mercedes?start=1",
            "http://e-opowiadania.pl/opowiadania/kryminal/122-historia-mercedes?start=2",
            "http://e-opowiadania.pl/opowiadania/milosc/112-przyjaciele-romantyczna-milosc?start=1",
            "http://e-opowiadania.pl/opowiadania/milosc/112-przyjaciele-romantyczna-milosc?start=2",
            "http://e-opowiadania.pl/opowiadania/milosc/158-dlug-wdziecznosci",
            "http://e-opowiadania.pl/opowiadania/milosc/97-rozdzialy/159-dlug-wdziecznosci-rozdzial-1",
            "http://e-opowiadania.pl/opowiadania/milosc/97-rozdzialy/159-dlug-wdziecznosci-rozdzial-1?start=1",
            "http://e-opowiadania.pl/opowiadania/milosc/97-rozdzialy/159-dlug-wdziecznosci-rozdzial-1?start=2",
            "http://e-opowiadania.pl/opowiadania/milosc/97-rozdzialy/159-dlug-wdziecznosci-rozdzial-1?start=3",
            "http://e-opowiadania.pl/opowiadania/milosc/97-rozdzialy/159-dlug-wdziecznosci-rozdzial-1?start=4"
    );

    public static void main(String[] args) {
        SentenceGenerator generator = new MarkovSentenceGenerator();

        SOURCE_URLS.forEach(url -> {
            try {
                generator.buildDictionary(new WebPageWordsSource(url));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read from URL: " + url, e);
            }
        });

        generator.generateSentence();
        generator.generateSentence();
        generator.generateSentence();
        generator.generateSentence();
        generator.generateSentence();
        generator.generateSentence();
    }
}
