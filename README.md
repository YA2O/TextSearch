# Text search

This program will read all the files in a given directory (staying at depth 1 in the directory tree), and 
then give a command prompt at which interactive searches can be performed.
 
## Instructions

Usage of the text search engine is e.g.
`sbt "run src/main/resources"`
in order to search for words in the files located in the directory *src/main/resources*. 

## Notes

* A word is any non-empty alphabetic string. No symbols, nor punctuation, 
nor numbers.
* Searching is case-insensitive.
* Matches for words are on whole words, so e.g. query with "boats" will not give a hit on "boat", and vice 
versa.
* Implementation of scoring is naive: the score of a file is simply the proportion of words from the query 
found in the file. In particular, matches on common words in English like "it", "and", "or", etc. have the 
same weight as any other words.
* Only files with UTF-8 encoding are searched into.
* If a file cannot be indexed, it is ignored in the search.


