# markov-generator

Simple generator of Markov chain sentences.

Usage:
```
SentenceGenerator generator = new MarkovSentenceGenerator();

WordsSource webSource = new WebPageWordsSource("https://github.com");
WordsSource stringSource = new StringWordsSource("I am a string words source.");

generator.buildDictionary(webSource, stringSource);
generator.generateSentence();
```
